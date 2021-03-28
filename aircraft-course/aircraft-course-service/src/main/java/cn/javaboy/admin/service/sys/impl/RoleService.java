package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.domain.sys.dto.RoleAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.RoleUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.RoleVO;
import cn.javaboy.admin.common.domain.sys.entity.RoleEntity;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.RoleResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.dao.sys.RoleDao;
import cn.javaboy.admin.dao.sys.RoleResourceDao;
import cn.javaboy.admin.dao.sys.RoleUserDao;
import cn.javaboy.admin.service.sys.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色管理业务
 *
 * @author listen
 * @date 2017/12/28 09:37
 */
@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private RoleUserDao roleUserDao;

    /**
     * 新增添加角色
     *
     * @param roleAddDTO
     * @return ResponseDTO
     */
    public ResponseDTO addRole(RoleAddDTO roleAddDTO) {
        RoleEntity userRoleEntity = roleDao.getByRoleName(roleAddDTO.getRoleName());
        if (null != userRoleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        RoleEntity roleEntity = JBBeanUtil.copy(roleAddDTO, RoleEntity.class);
        roleDao.insert(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据角色id 删除
     *
     * @param roleId
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO deleteRole(Long roleId) {
        RoleEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        roleDao.deleteById(roleId);
        roleResourceDao.deleteByRoleId(roleId);
        roleUserDao.deleteByRoleId(roleId);
        return ResponseDTO.succ();
    }

    /**
     * 更新角色
     *
     * @param roleUpdateDTO
     * @return ResponseDTO
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateRole(RoleUpdateDTO roleUpdateDTO) {
        if (null == roleDao.selectById(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        RoleEntity userRoleEntity = roleDao.getByRoleName(roleUpdateDTO.getRoleName());
        if (null != userRoleEntity && ! userRoleEntity.getId().equals(roleUpdateDTO.getId())) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NAME_EXISTS);
        }
        RoleEntity roleEntity = JBBeanUtil.copy(roleUpdateDTO, RoleEntity.class);
        roleDao.updateById(roleEntity);
        return ResponseDTO.succ();
    }

    /**
     * 根据id获取角色数据
     *
     * @param roleId
     * @return ResponseDTO<RoleDTO>
     */
    public ResponseDTO<RoleVO> getRoleById(Long roleId) {
        RoleEntity roleEntity = roleDao.selectById(roleId);
        if (null == roleEntity) {
            return ResponseDTO.wrap(RoleResponseCodeConst.ROLE_NOT_EXISTS);
        }
        RoleVO role = JBBeanUtil.copy(roleEntity, RoleVO.class);
        return ResponseDTO.succData(role);
    }

    /**
     * 获取所有角色列表
     *
     * @return ResponseDTO
     */
    public ResponseDTO<List<RoleVO>> getAllRole() {
        List<RoleEntity> roleEntityList = roleDao.selectList(null);
        List<RoleVO> roleList = JBBeanUtil.copyList(roleEntityList, RoleVO.class);
        return ResponseDTO.succData(roleList);
    }
}
