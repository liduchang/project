package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @date
 * @since JDK1.8
 */
@Data
public class DepartmentUpdateDTO extends DepartmentCreateDTO {

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long id;

}
