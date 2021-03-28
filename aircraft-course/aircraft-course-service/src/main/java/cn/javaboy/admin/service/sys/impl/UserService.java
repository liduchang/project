package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.constant.CommonConst;
import cn.javaboy.admin.common.domain.constant.UserStatusEnum;
import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.sys.bo.RequestTokenBO;
import cn.javaboy.admin.common.domain.sys.bo.UserBO;
import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.entity.RoleUserEntity;
import cn.javaboy.admin.common.domain.sys.entity.DepartmentEntity;
import cn.javaboy.admin.common.domain.sys.entity.UserEntity;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.UserResponseCodeConst;
import cn.javaboy.admin.common.utils.JBVerificationUtil;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBDigestUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.DepartmentDao;
import cn.javaboy.admin.dao.sys.PositionDao;
import cn.javaboy.admin.dao.sys.RoleUserDao;
import cn.javaboy.admin.dao.sys.UserDao;
import cn.javaboy.admin.service.sys.IUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 10:40
 */
@Service
public class UserService implements IUserService {

    private static final String RESET_PASSWORD = "123456";

    @Autowired
    private UserDao userDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private PositionService positionService;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private ResourceUserService resourceUserService;

    /**
     * 员工基本信息的缓存
     */
    private static final ConcurrentHashMap<Long, UserBO> userCache = new ConcurrentHashMap<>();

    public List<UserVO> getAllUser() {
        return userDao.selectAll();
    }

    public UserBO getById(Long id) {
        UserBO userBO = userCache.get(id);
        if (userBO == null) {
            UserEntity userEntity = userDao.selectById(id);
            if (userEntity != null) {
                Boolean superman = resourceUserService.isSuperman(id);
                userBO = new UserBO(userEntity, superman);
                userCache.put(userEntity.getId(), userBO);
            }
        }
        return userBO;
    }

    /**
     * 查询员工列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<UserVO>> selectUserList(UserQueryDTO queryDTO) {
        Page pageParam = JBPageUtil.convert2QueryPage(queryDTO);
        queryDTO.setIsDelete(JudgeEnum.NO.getValue());
        List<UserDTO> userList = userDao.selectUserList(pageParam, queryDTO);
        List<Long> userIdList = userList.stream().map(UserDTO::getId).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(userIdList)) {
            List<PositionRelationResultDTO> positionRelationResultDTOList = positionDao.selectUserRelation(userIdList);
            Map<Long, List<PositionRelationResultDTO>> userPositionMap = new HashedMap();

            for (PositionRelationResultDTO positionRelationResultDTO : positionRelationResultDTOList) {
                List<PositionRelationResultDTO> relationResultDTOList = userPositionMap.get(positionRelationResultDTO.getUserId());
                //匹配对应的岗位
                if (relationResultDTOList == null) {
                    relationResultDTOList = new ArrayList<>();
                    userPositionMap.put(positionRelationResultDTO.getUserId(), relationResultDTOList);
                }
                relationResultDTOList.add(positionRelationResultDTO);
            }

            for (UserDTO userDTO : userList) {
                List<PositionRelationResultDTO> relationResultDTOList = userPositionMap.get(userDTO.getId());
                if (relationResultDTOList != null) {
                    userDTO.setPositionRelationList(relationResultDTOList);
                    userDTO.setPositionName(relationResultDTOList.stream().map(PositionRelationResultDTO::getPositionName).collect(Collectors.joining(",")));
                }
            }
        }
        return ResponseDTO.succData(JBPageUtil.convert2PageResult(pageParam, userList, UserVO.class));
    }

    /**
     * 新增员工
     *
     * @param userAddDto
     * @param requestToken
     * @return
     */
    public ResponseDTO<String> addUser(UserAddDTO userAddDto, RequestTokenBO requestToken) {
        UserEntity entity = JBBeanUtil.copy(userAddDto, UserEntity.class);
        if (StringUtils.isNotEmpty(userAddDto.getIdCard())) {
            boolean checkResult = Pattern.matches(JBVerificationUtil.ID_CARD, userAddDto.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(UserResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(userAddDto.getBirthday())) {
            boolean checkResult = Pattern.matches(JBVerificationUtil.DATE, userAddDto.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(UserResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        //同名员工
        UserDTO sameNameUser = userDao.getByLoginName(entity.getLoginName(), UserStatusEnum.NORMAL.getValue());
        if (null != sameNameUser) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        //同电话员工
        UserDTO samePhoneUser = userDao.getByPhone(entity.getPhone(), UserStatusEnum.NORMAL.getValue());
        if (null != samePhoneUser) {
            return ResponseDTO.wrap(UserResponseCodeConst.PHONE_EXISTS);
        }
        Long departmentId = entity.getDepartmentId();
        DepartmentEntity department = departmentDao.selectById(departmentId);
        if (department == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.DEPT_NOT_EXIST);
        }

        //如果没有密码  默认设置为123456
        String pwd = entity.getLoginPwd();
        if (StringUtils.isBlank(pwd)) {
            entity.setLoginPwd(JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD));
        } else {
            entity.setLoginPwd(JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, entity.getLoginPwd()));
        }

        entity.setCreateUser(requestToken.getRequestUserId());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        userDao.insert(entity);

        PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(userAddDto.getPositionIdList(), entity.getId());
        //存储所选岗位信息
        positionService.addPositionRelation(positionRelAddDTO);

        return ResponseDTO.succ();
    }

    /**
     * 更新禁用状态
     *
     * @param userId
     * @param status
     * @return
     */
    public ResponseDTO<String> updateStatus(Long userId, Integer status) {
        if (null == userId) {
            return ResponseDTO.wrap(UserResponseCodeConst.EMP_NOT_EXISTS);
        }
        UserBO entity = getById(userId);
        if (null == entity) {
            return ResponseDTO.wrap(UserResponseCodeConst.EMP_NOT_EXISTS);
        }
        List<Long> empIds = Lists.newArrayList();
        empIds.add(userId);
        userDao.batchUpdateStatus(empIds, status);
        userCache.remove(userId);
        return ResponseDTO.succ();
    }

    /**
     * 批量更新员工状态
     *
     * @param batchUpdateStatusDTO
     * @return
     */
    public ResponseDTO<String> batchUpdateStatus(UserBatchUpdateStatusDTO batchUpdateStatusDTO) {
        userDao.batchUpdateStatus(batchUpdateStatusDTO.getUserIds(), batchUpdateStatusDTO.getStatus());
        if (batchUpdateStatusDTO.getUserIds() != null) {
            batchUpdateStatusDTO.getUserIds().forEach(e -> userCache.remove(e));
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新员工
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateUser(UserUpdateDTO updateDTO) {
        Long userId = updateDTO.getId();
        UserEntity userEntity = userDao.selectById(userId);
        if (null == userEntity) {
            return ResponseDTO.wrap(UserResponseCodeConst.EMP_NOT_EXISTS);
        }
        if (StringUtils.isNotBlank(updateDTO.getIdCard())) {
            boolean checkResult = Pattern.matches(JBVerificationUtil.ID_CARD, updateDTO.getIdCard());
            if (!checkResult) {
                return ResponseDTO.wrap(UserResponseCodeConst.ID_CARD_ERROR);
            }
        }
        if (StringUtils.isNotEmpty(updateDTO.getBirthday())) {
            boolean checkResult = Pattern.matches(JBVerificationUtil.DATE, updateDTO.getBirthday());
            if (!checkResult) {
                return ResponseDTO.wrap(UserResponseCodeConst.BIRTHDAY_ERROR);
            }
        }
        Long departmentId = updateDTO.getDepartmentId();
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(UserResponseCodeConst.DEPT_NOT_EXIST);
        }
        UserDTO sameNameUser = userDao.getByLoginName(updateDTO.getLoginName(), UserStatusEnum.NORMAL.getValue());
        if (null != sameNameUser && !sameNameUser.getId().equals(userId)) {
            return ResponseDTO.wrap(UserResponseCodeConst.LOGIN_NAME_EXISTS);
        }
        UserDTO samePhoneUser = userDao.getByPhone(updateDTO.getLoginName(), UserStatusEnum.NORMAL.getValue());
        if (null != samePhoneUser && !samePhoneUser.getId().equals(userId)) {
            return ResponseDTO.wrap(UserResponseCodeConst.PHONE_EXISTS);
        }
        String newPwd = updateDTO.getLoginPwd();
        if (!StringUtils.isBlank(newPwd)) {
            updateDTO.setLoginPwd(JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updateDTO.getLoginPwd()));
        } else {
            updateDTO.setLoginPwd(userEntity.getLoginPwd());
        }
        UserEntity entity = JBBeanUtil.copy(updateDTO, UserEntity.class);
        entity.setUpdateTime(new Date());
        if (StringUtils.isEmpty(entity.getBirthday())) {
            entity.setBirthday(null);
        }
        if (CollectionUtils.isNotEmpty(updateDTO.getPositionIdList())) {
            //删除旧的关联关系 添加新的关联关系
            positionService.removePositionRelation(entity.getId());
            PositionRelationAddDTO positionRelAddDTO = new PositionRelationAddDTO(updateDTO.getPositionIdList(), entity.getId());
            positionService.addPositionRelation(positionRelAddDTO);
        }
        entity.setIsDisabled(userEntity.getIsDisabled());
        entity.setIsLeave(userEntity.getIsLeave());
        entity.setCreateUser(userEntity.getCreateUser());
        entity.setCreateTime(userEntity.getCreateTime());
        entity.setUpdateTime(new Date());
        userDao.updateById(entity);
        userCache.remove(userId);
        return ResponseDTO.succ();
    }

    /**
     * 删除员工
     *
     * @param userId 员工ID
     * @return
     */
    public ResponseDTO<String> deleteUserById(Long userId) {
        UserEntity userEntity = userDao.selectById(userId);
        if (null == userEntity) {
            return ResponseDTO.wrap(UserResponseCodeConst.EMP_NOT_EXISTS);
        }
        //假删
        userEntity.setIsDelete(JudgeEnum.YES.getValue().longValue());
        userDao.updateById(userEntity);
        userCache.remove(userId);
        return ResponseDTO.succ();
    }

    /**
     * 更新用户角色
     *
     * @param updateRolesDTO
     * @return
     */
    public ResponseDTO<String> updateRoles(UserUpdateRolesDTO updateRolesDTO) {
        roleUserDao.deleteByUserId(updateRolesDTO.getUserId());
        if (CollectionUtils.isNotEmpty(updateRolesDTO.getRoleIds())) {
            List<RoleUserEntity> roleUserEntities = Lists.newArrayList();
            RoleUserEntity roleUserEntity;
            for (Long roleId : updateRolesDTO.getRoleIds()) {
                roleUserEntity = new RoleUserEntity();
                roleUserEntity.setUserId(updateRolesDTO.getUserId());
                roleUserEntity.setRoleId(roleId);
                roleUserEntities.add(roleUserEntity);
            }
            roleUserDao.batchInsert(roleUserEntities);
        }
        return ResponseDTO.succ();
    }

    /**
     * 更新密码
     *
     * @param updatePwdDTO
     * @param requestToken
     * @return
     */
    public ResponseDTO<String> updatePwd(UserUpdatePwdDTO updatePwdDTO, RequestTokenBO requestToken) {
        Long userId = requestToken.getRequestUserId();
        UserEntity user = userDao.selectById(userId);
        if (user == null) return ResponseDTO.wrap(UserResponseCodeConst.EMP_NOT_EXISTS);

        if (!user.getLoginPwd().equals(JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getOldPwd()))) {
            return ResponseDTO.wrap(UserResponseCodeConst.PASSWORD_ERROR);
        }
        user.setLoginPwd(JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, updatePwdDTO.getPwd()));
        userDao.updateById(user);
        userCache.remove(userId);
        return ResponseDTO.succ();
    }

    public ResponseDTO<List<UserVO>> getUserByDeptId(Long departmentId) {
        List<UserVO> list = userDao.getUserIdByDeptId(departmentId);
        return ResponseDTO.succData(list);
    }

    /**
     * 重置密码
     *
     * @param userId
     * @return
     */
    public ResponseDTO resetPasswd(Integer userId) {
        String md5Password = JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, RESET_PASSWORD);
        userDao.updatePassword(userId, md5Password);
        userCache.remove(userId);
        return ResponseDTO.succ();
    }
}
