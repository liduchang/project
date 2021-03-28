package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.DepartmentCreateDTO;
import cn.javaboy.admin.common.domain.sys.dto.DepartmentUpdateDTO;
import cn.javaboy.admin.common.domain.sys.dto.UserDTO;
import cn.javaboy.admin.common.domain.sys.entity.DepartmentEntity;
import cn.javaboy.admin.common.domain.sys.vo.DepartmentVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.DepartmentResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.sys.DepartmentDao;
import cn.javaboy.admin.dao.sys.UserDao;
import cn.javaboy.admin.service.sys.IDepartmentService;
import cn.javaboy.admin.service.sys.IDepartmentTreeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 10:36
 */
@Slf4j
@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private IDepartmentTreeService departmentTreeService;

    /**
     * 获取部门树形结构
     *
     * @return
     */
    public ResponseDTO<List<DepartmentVO>> listDepartment() {
        List<DepartmentVO> departmentVOList = departmentDao.listAll();
        List<DepartmentVO> result = departmentTreeService.buildTree(departmentVOList);
        return ResponseDTO.succData(result);
    }

    /**
     * 获取所有部门和员工信息
     *
     * @param departmentName
     * @return
     */
    public ResponseDTO<List<DepartmentVO>> listAllDepartmentUser(String departmentName) {

        // 获取全部部门列表
        List<DepartmentVO> departmentVOList = departmentDao.listAll();
        if (StringUtils.isNotBlank(departmentName)) {
            // 检索条件不为空的时候 过滤部门列表
            departmentVOList = filterDepartment(departmentVOList, departmentName);
        }

        Map<Long, DepartmentVO> departmentMap = departmentVOList.stream().collect(Collectors.toMap(DepartmentVO::getId, Function.identity()));
        // 获取全部员工列表
        List<UserDTO> userList = userDao.listAll();
        userList.forEach(userDTO -> {

            DepartmentVO departmentVO = departmentMap.get(userDTO.getDepartmentId());
            if (null == departmentVO) {
                return;
            }
            List<UserDTO> userDTOList = departmentVO.getUsers();
            if (null == userDTOList) {
                userDTOList = new ArrayList<>();
            }
            userDTOList.add(userDTO);
            departmentVO.setUsers(userDTOList);
        });
        List<DepartmentVO> result = departmentTreeService.buildTree(departmentVOList);
        return ResponseDTO.succData(result);
    }

    /**
     * 过滤部门名称，获取过滤后的结果
     *
     * @author lidoudou
     * @date 2019/4/28 20:17
     */
    private List<DepartmentVO> filterDepartment(List<DepartmentVO> departmentVOList, String departmentName) {
        Map<Long, DepartmentVO> departmentMap = new HashMap<>();
        departmentVOList.forEach(item -> {
            if (item.getName().indexOf(departmentName) < 0) {
                return;
            }
            // 当前部门包含关键字
            departmentMap.put(item.getId(), item);
            Long parentId = item.getParentId();
            if (null != parentId) {
                List<DepartmentVO> filterResult = new ArrayList<>();
                getParentDepartment(departmentVOList, parentId, filterResult);
                for (DepartmentVO dto : filterResult) {
                    if (!departmentMap.containsKey(dto.getId())) {
                        departmentMap.put(dto.getId(), dto);
                    }
                }
            }
        });
        return departmentMap.values().stream().collect(Collectors.toList());
    }

    private List<DepartmentVO> getParentDepartment(List<DepartmentVO> departmentVOList, Long parentId, List<DepartmentVO> result) {
        List<DepartmentVO> deptList = departmentVOList.stream().filter(e -> e.getId().equals(parentId)).collect(Collectors.toList());
        for (DepartmentVO item : deptList) {
            result.add(item);
            if (item.getParentId() != 0 && item.getParentId() != null) {
                result.addAll(getParentDepartment(departmentVOList, item.getParentId(), result));
            }
        }
        return result;
    }

    /**
     * 新增添加部门
     *
     * @param departmentCreateDTO
     * @return AjaxResult
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> addDepartment(DepartmentCreateDTO departmentCreateDTO) {
        DepartmentEntity departmentEntity = JBBeanUtil.copy(departmentCreateDTO, DepartmentEntity.class);
        departmentEntity.setSort(0L);
        departmentDao.insert(departmentEntity);
        departmentEntity.setSort(departmentEntity.getId());
        departmentDao.updateById(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 更新部门信息
     *
     * @param updateDTO
     * @return AjaxResult<String>
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateDepartment(DepartmentUpdateDTO updateDTO) {
        if (updateDTO.getParentId() == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.PARENT_ID_ERROR);
        }
        DepartmentEntity entity = departmentDao.selectById(updateDTO.getId());
        if (entity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        DepartmentEntity departmentEntity = JBBeanUtil.copy(updateDTO, DepartmentEntity.class);
        departmentEntity.setSort(entity.getSort());
        departmentDao.updateById(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id删除部门
     * 1、需要判断当前部门是否有子部门,有子部门则不允许删除
     * 2、需要判断当前部门是否有员工，有员工则不能删除
     *
     * @param deptId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delDepartment(Long deptId) {
        DepartmentEntity departmentEntity = departmentDao.selectById(deptId);
        if (null == departmentEntity) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.DEPT_NOT_EXISTS);
        }
        // 是否有子级部门
        int subDepartmentNum = departmentDao.countSubDepartment(deptId);
        if (subDepartmentNum > 0) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.CANNOT_DEL_DEPARTMENT_WITH_CHILD);
        }

        // 是否有未删除员工
        int userNum = userDao.countByDepartmentId(deptId, false);
        if (userNum > 0) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.CANNOT_DEL_DEPARTMENT_WITH_EMPLOYEE);
        }
        departmentDao.deleteById(deptId);
        return ResponseDTO.succ();
    }

    /**
     * 根据id获取部门信息
     *
     * @param departmentId
     * @return AjaxResult<DepartmentVO>
     */
    public ResponseDTO<DepartmentVO> getDepartmentById(Long departmentId) {
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.DEPT_NOT_EXISTS);
        }
        DepartmentVO departmentVO = JBBeanUtil.copy(departmentEntity, DepartmentVO.class);
        return ResponseDTO.succData(departmentVO);
    }

    /**
     * 获取所有部门
     *
     * @return
     */
    public ResponseDTO<List<DepartmentVO>> listAll() {
        List<DepartmentVO> departmentVOList = departmentDao.listAll();
        return ResponseDTO.succData(departmentVOList);
    }

    /**
     * 上下移动
     *
     * @param departmentId
     * @param swapId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> upOrDown(Long departmentId, Long swapId) {
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        DepartmentEntity swapEntity = departmentDao.selectById(swapId);
        if (swapEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        Long departmentSort = departmentEntity.getSort();
        departmentEntity.setSort(swapEntity.getSort());
        departmentDao.updateById(departmentEntity);
        swapEntity.setSort(departmentSort);
        departmentDao.updateById(swapEntity);
        return ResponseDTO.succ();
    }

    /**
     * 部门升级
     *
     * @param departmentId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> upgrade(Long departmentId) {
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        if (departmentEntity.getParentId() == null || departmentEntity.getParentId().equals(0)) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.ERROR_PARAM, "此部门已经是根节点无法移动");
        }
        DepartmentEntity parentEntity = departmentDao.selectById(departmentEntity.getParentId());

        departmentEntity.setParentId(parentEntity.getParentId());
        departmentDao.updateById(departmentEntity);
        return ResponseDTO.succ();
    }

    /**
     * 部门降级
     *
     * @param departmentId
     * @param preId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> downgrade(Long departmentId, Long preId) {
        DepartmentEntity departmentEntity = departmentDao.selectById(departmentId);
        if (departmentEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        DepartmentEntity preEntity = departmentDao.selectById(preId);
        if (preEntity == null) {
            return ResponseDTO.wrap(DepartmentResponseCodeConst.NOT_EXISTS);
        }
        departmentEntity.setParentId(preEntity.getId());
        departmentDao.updateById(departmentEntity);
        return ResponseDTO.succ();
    }
}
