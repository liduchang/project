package cn.javaboy.admin.common.domain.sys.vo;

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
public class RoleSelectedVO extends RoleVO {

    @ApiModelProperty("角色名称")
    private Boolean selected;
}
