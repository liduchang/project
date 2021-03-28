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
public class RoleVO {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色备注")
    private String remark;
}
