package cn.javaboy.admin.common.resp.constant;

/**
 * 
 * [ 5001-5999 ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author yandanyang
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 */
public class SysParamResponseCodeConst extends ResponseCodeConst {

    /**
     * 配置参数已存在 10201
     */
    public static final SysParamResponseCodeConst ALREADY_EXIST = new SysParamResponseCodeConst(5001, "配置参数已存在");
     /**
     * 配置参数不存在 10203
     */
    public static final SysParamResponseCodeConst NOT_EXIST = new SysParamResponseCodeConst(5002, "配置参数不存在");

    public SysParamResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}
