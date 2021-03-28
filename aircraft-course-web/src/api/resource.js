import { postAxios, getAxios } from '@/lib/http';

export const resourceApi = {
  // 获取所有请求路径
  getAllUrl: data => {
    return getAxios('/resource/getAllUrl');
  },
  // 获取全部菜单列表
  getMenuList: data => {
    return postAxios('/resource/menu/queryAll');
  },
  // 菜单批量保存
  addBatchSaveMenu: data => {
    return postAxios('/resource/menu/batchSaveMenu', data);
  },
  // 功能点批量保存
  addBatchSavePoints: data => {
    return postAxios('/resource/function/batchSave', data);
  },
  // 查询菜单功能点
  queryPrivilegeFunctionList: menuKey => {
    return postAxios('/resource/function/query/' + menuKey);
  },
  // 保存更新功能点
  addOrUpdate: data => {
    return postAxios('/resource/function/saveOrUpdate', data);
  },
  // 更新角色权限
  getRolePower: data => {
    return postAxios('/resource/updateRolePrivilege', data);
  },
  // 获取角色可选的功能权限
  getListPrivilegeByRoleId: id => {
    return getAxios('/resource/listPrivilegeByRoleId/' + id);
  }
};
