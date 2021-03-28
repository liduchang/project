package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.entity.DepartmentEntity;
import cn.javaboy.admin.common.domain.sys.entity.RoleEntity;
import cn.javaboy.admin.common.domain.sys.entity.RoleUserEntity;
import cn.javaboy.admin.common.domain.sys.vo.RoleSelectedVO;
import cn.javaboy.admin.common.domain.sys.vo.UserVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.RoleResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.DepartmentDao;
import cn.javaboy.admin.dao.sys.RoleDao;
import cn.javaboy.admin.dao.sys.RoleUserDao;
import cn.javaboy.admin.service.sys.IRoleUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 10:04
 */
@Service
public class RoleUserService implements IRoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * 通过角色id，分页获取成员用户列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<UserVO>> listUserByName(RoleQueryDTO queryDTO) {
        Page page = JBPageUtil.convert2QueryPage(queryDTO);
        List<UserDTO> userDTOS = roleUserDao.selectUserByNamePage(page, queryDTO);
        userDTOS.stream().filter(e -> e.getDepartmentId() != null).forEach(userDTO -> {
            DepartmentEntity departmentEntity = departmentDao.selectById(userDTO.getDepartmentId());
            userDTO.setDepartmentName(departmentEntity.getName());
        });
        PageResultDTO<UserVO> pageResultDTO = JBPageUtil.convert2PageResult(page, userDTOS, UserVO.class);
        return ResponseDTO.succData(pageResultDTO);
    }

    public ResponseDTO<List<UserVO>> getAllUserByRoleId(Long roleId) {
        List<UserDTO> userDTOS = roleUserDao.selectUserByRoleId(roleId);
        List<UserVO> list = JBBeanUtil.copyList(userDTOS, UserVO.class);
        return ResponseDTO.succData(list);
    }

    /**
     * 移除用户角色
     *
     * @param userId
     * @param roleId
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> removeUserRole(Long userId, Long roleId) {
        if (null == userId || null == roleId) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ERROR_PARAM);
        }
        roleUserDao.deleteByUserIdRoleId(userId, roleId);
        return ResponseDTO.succ();
    }

    /**
     * 批量删除角色的成员用户
     *
     * @param removeDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchRemoveUserRole(RoleBatchDTO removeDTO) {
        List<Long> userIdList = removeDTO.getUserIds();
        roleUserDao.batchDeleteUserRole(removeDTO.getRoleId(), userIdList);
        return ResponseDTO.succ();
    }

    /**
     * 批量添加角色的成员用户
     *
     * @param addDTO
     * @return ResponseDTO<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchAddUserRole(RoleBatchDTO addDTO) {
        Long roleId = addDTO.getRoleId();
        List<Long> userIdList = addDTO.getUserIds();
        roleUserDao.deleteByRoleId(roleId);
        List<RoleUserEntity> roleRelationEntities = Lists.newArrayList();
        RoleUserEntity userRoleRelationEntity;
        for (Long userId : userIdList) {
            userRoleRelationEntity = new RoleUserEntity();
            userRoleRelationEntity.setRoleId(roleId);
            userRoleRelationEntity.setUserId(userId);
            roleRelationEntities.add(userRoleRelationEntity);
        }
        roleUserDao.batchInsert(roleRelationEntities);
        return ResponseDTO.succ();
    }

    /**
     * 通过用户id获取用户角色
     *
     * @param userId
     * @return
     */
    public ResponseDTO<List<RoleSelectedVO>> getRolesByUserId(Long userId) {
        List<Long> roleIds = roleUserDao.selectRoleIdByUserId(userId);
        List<RoleEntity> roleList = roleDao.selectList(null);
        List<RoleSelectedVO> result = JBBeanUtil.copyList(roleList, RoleSelectedVO.class);
        result.stream().forEach(item -> item.setSelected(roleIds.contains(item.getId())));
        return ResponseDTO.succData(result);
    }
}
