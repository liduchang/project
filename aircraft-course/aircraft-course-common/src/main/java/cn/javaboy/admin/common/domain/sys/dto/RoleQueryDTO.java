package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
@Data
public class RoleQueryDTO extends PageParamDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色id")
    private String roleId;
}
