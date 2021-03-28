package cn.javaboy.admin.common.resp.constant;

/**
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
public class ResourceResponseCodeConst extends ResponseCodeConst {

    public static final ResourceResponseCodeConst PRIVILEGE_NOT_EXISTS = new ResourceResponseCodeConst(7001, "当前数据不存在，请联系你的管理员！");

    public static final ResourceResponseCodeConst ROUTER_KEY_NO_REPEAT = new ResourceResponseCodeConst(7002, "模块和页面的“功能Key”值不能重复！");

    public static final ResourceResponseCodeConst MENU_NOT_EXIST = new ResourceResponseCodeConst(7003, "菜单不存在，清先保存菜单！");

    public static final ResourceResponseCodeConst POINT_NOT_EXIST = new ResourceResponseCodeConst(7004, "功能点不存在，清先保存功能点！");

    public ResourceResponseCodeConst(int code, String msg) {
        super(code, msg);
    }
}

