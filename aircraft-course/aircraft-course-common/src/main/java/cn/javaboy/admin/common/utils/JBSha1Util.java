package cn.javaboy.admin.common.utils;

/**
 * @Author: YinZhi
 * @Description:
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JBSha1Util {

    public static String getSha1(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String pwd = "admin";
        String pwdAfterSha1 = JBSha1Util.getSha1(pwd.getBytes());
        System.out.println("原始密码："+pwd);
        System.out.println("sha1加密："+pwdAfterSha1);
    }
}
