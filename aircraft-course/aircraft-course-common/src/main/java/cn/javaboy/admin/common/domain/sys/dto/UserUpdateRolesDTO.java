package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2019 1024lab.netInc. All rights reserved.
 * @date
 * @since JDK1.8
 */
@Data
public class UserUpdateRolesDTO {

    @ApiModelProperty("员工id")
    @NotNull(message = "员工id不能为空")
    private Long userId;

    @ApiModelProperty("角色ids")
    private List<Long> roleIds;

}
