// 用户日志API
import {
  postAxios,
  getAxios
} from '@/lib/http.js';
export const userLogApi = {
  // 查询用户操作日志
  getUserOperateLogPage: (data) => {
    return postAxios('log/userOperateLog/page/query', data);
  },
  // 用户操作日志详情
  detailUserOperateLog: (data) => {
    return getAxios('log/userOperateLog/detail/' + data);
  },
  // 删除用户操作日志
  deleteUserOperateLog: (data) => {
    return getAxios('log/userOperateLog/delete/' + data);
  },
  // 查询用户登录日志
  getUserLoginLogPage: (data) => {
    return postAxios('log/userLoginLog/page/query', data);
  },
  // 删除用户登录日志
  deleteUserLoginLog: (data) => {
    return getAxios('log/userLoginLog/delete/' + data);
  }

};
