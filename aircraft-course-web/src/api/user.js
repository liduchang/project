import { postAxios, getAxios } from '@/lib/http';
export const userApi = {
  // 用户管理查询
  getUserList: (data) => {
    return postAxios('/user/query', data);
  },
  // 添加用户
  addUser: (data) => {
    return postAxios('/user/add', data);
  },
  // 更新用户信息
  updateUser: (data) => {
    return postAxios('/user/update', data);
  },
  // 禁用启用单个用户
  updateStatus: (userId, status) => {
    return getAxios('/user/updateStatus/' + userId + '/' + status);
  },
  // 批量禁用
  updateStatusBatch: (data) => {
    return postAxios('/user/batchUpdateStatus', data);
  },
  // 单个用户角色授权
  updateRoles: (data) => {
    return postAxios('/user/updateRoles', data);
  },
  // 修改密码
  updatePwd: (data) => {
    return postAxios('/user/updatePwd', data);
  },
  // 重置密码
  resetPassword: (userId) => {
    return getAxios('/user/resetPasswd/' + userId);
  },
  // 通过部门id获取当前部门的人员&没有部门的人
  getListUserByDeptId: (departmentId) => {
    return getAxios('/user/listUserByDeptId/' + departmentId);
  },
  // 删除用户
  deleteUser: (userId) => {
    return postAxios('/user/delete/' + userId);
  }
};
