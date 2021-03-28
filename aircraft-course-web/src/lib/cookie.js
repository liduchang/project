import Cookies from 'js-cookie';
import config from '@/config';
const { cookieExpires } = config;
export const TOKEN_KEY = 'token';

export default {
  setToken: token => {
    let millisecond = new Date().getTime();
    // 默认有效时间是15分钟
    let expiresTime = new Date(millisecond + 60 * 1000 * 15);
    Cookies.set(TOKEN_KEY, token, {
      // token在Cookie中存储的天数，默认1天
      expires: expiresTime
    });
  },
  getToken: () => {
    const token = Cookies.get(TOKEN_KEY);
    if (token) return token;
    else return null;
  },
  clearToken: () => {
    Cookies.remove(TOKEN_KEY);
  }
};
