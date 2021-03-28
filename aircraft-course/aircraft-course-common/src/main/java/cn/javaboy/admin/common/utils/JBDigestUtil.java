package cn.javaboy.admin.common.utils;

import cn.javaboy.admin.common.domain.constant.CommonConst;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;

public class JBDigestUtil extends DigestUtils {

    /**
     * md5加盐加密
     *
     * @param salt
     * @param password
     * @return
     */
    public static String encryptPassword(String salt, String password) {
        return JBDigestUtil.md5Hex(String.format(salt, password));
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String string ="123456";
        String sha1 = JBSha1Util.getSha1(string.getBytes());
        String s = JBDigestUtil.encryptPassword(CommonConst.Password.SALT_FORMAT, sha1);
        System.err.println("s:"+s);
    }
}
