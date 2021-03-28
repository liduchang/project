package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleResourceTreeVO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("权限列表")
    private List<RoleResourceSimpleDTO> privilege;

    @ApiModelProperty("选中的权限")
    private List<String> selectedKey;
}
