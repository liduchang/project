package cn.javaboy.admin.common.domain.constant;

import cn.javaboy.admin.common.utils.JBVerificationUtil;

public enum SysParamDataType {
    /**
     * 整数
     */
    INTEGER(JBVerificationUtil.INTEGER),
    /**
     * 文本
     */
    TEXT(null),
    /**
     * url地址
     */
    URL(JBVerificationUtil.URL),
    /**
     *  邮箱
     */
    EMAIL(JBVerificationUtil.EMAIL),
    /**
     * JSON 字符串
     */
    JSON(null),
    /**
     * 2019-08
     */
    YEAR_MONTH(JBVerificationUtil.YEAR_MONTH),
    /**
     * 2019-08-01
     */
    DATE(JBVerificationUtil.DATE),
    /**
     * 2019-08-01 10:23
     */
    DATE_TIME(JBVerificationUtil.DATE_TIME),
    /**
     * 10:23-10:56
     */
    TIME_SECTION(JBVerificationUtil.TIME_SECTION),
    /**
     * 10:23
     */
    TIME(JBVerificationUtil.TIME);

    private String valid;


    SysParamDataType(String valid){
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }
}
