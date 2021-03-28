import { postAxios, getAxios } from '@/lib/http';
export const roleApi = {
  // 添加角色
  addRole: (remark, roleName) => {
    const data = {
      remark: remark,
      roleName: roleName
    };
    return postAxios('/role/add', data);
  },
  // 删除角色
  deleteRole: id => {
    return getAxios('/role/delete/' + id);
  },
  // 修改角色
  updateRole: (id, remark, roleName) => {
    const data = {
      id: id,
      remark: remark,
      roleName: roleName
    };
    return postAxios('/role/update', data);
  },
  // 获取角色数据
  getRoleDetail: id => {
    return getAxios('/role/get/' + id);
  },
  // 加载角色列表
  getAllRole: () => {
    return getAxios('/role/getAll');
  },
  // 根据角色名字获取对应成员列表
  getListUser: data => {
    return postAxios('/role/listUser', data);
  },
  // 根据角色id获取角色成员-员工列表
  getAllListUser: id => {
    return getAxios('/role/listAllUser/' + id);
  },
  // 从角色成员列表中移除员工
  deleteUserRole: param => {
    return getAxios('/role/removeUser?userId=' + param.userId + '&roleId=' + param.roleId);
  },
  // 从角色成员列表中批量移除员工
  deleteUserList: data => {
    return postAxios('/role/removeUserList', data);
  },

  // 添加角色成员方法
  addUserListRole: data => {
    return postAxios('/role/addUserList', data);
  },
  // 通过员工id获取所有角色以及员工具有的角色
  getRoles: id => {
    return getAxios('/role/getRoles/' + id);
  }

};
