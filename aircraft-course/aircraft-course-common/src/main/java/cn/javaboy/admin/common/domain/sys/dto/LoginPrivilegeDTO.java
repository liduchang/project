package cn.javaboy.admin.common.domain.sys.dto;

import cn.javaboy.admin.common.anno.ApiModelPropertyEnum;
import cn.javaboy.admin.common.domain.constant.ResourceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * [  ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
public class LoginPrivilegeDTO {

    @ApiModelProperty("权限key")
    private String key;

    @ApiModelPropertyEnum(enumDesc = "菜单类型",value = ResourceTypeEnum.class)
    private Integer type;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("父级key")
    private String parentKey;

}
