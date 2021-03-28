// 系统参数API
import {
  postAxios,
  getAxios
} from '@/lib/http';
export const systemParamApi = {
  // 查询系统参数列表
  getSystemConfigList: (data) => {
    return postAxios('/param/getListPage', data);
  },
  // 添加系统参数
  addSystemConfig: (data) => {
    return postAxios('/param/add', data);
  },
  // 更新单条系统参数
  updateSystemConfig: (data) => {
    return postAxios('/param/update', data);
  },
  // 通过key获取对应的信息
  getConfigListByKey: (key) => {
    return getAxios(`/param/selectByKey?configKey=${key}`);
  },
  // 根据分组查询所有系统配置
  getListByGroup: (group) => {
    return getAxios(`/param/getListByGroup?group=${group}`);
  },
  // 获取系统版本信息
  getCodeVersion: () => {
    return getAxios('/param/codeVersion');
  }
};
