package cn.javaboy.admin.common.domain.sys.vo;

import lombok.Data;

/**
 * [  ]
 *
 * @author yandanyang
 * @since JDK1.8
 */
@Data
public class KaptchaVO {

    /**
     *  验证码UUID
     */
    private String uuid;

    /**
     * base64 验证码
     */
    private String code;

}
