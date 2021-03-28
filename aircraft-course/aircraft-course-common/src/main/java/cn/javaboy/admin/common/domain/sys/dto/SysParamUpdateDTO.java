package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author yandanyang
 * @date
 */
@Data
public class SysParamUpdateDTO extends SysParamAddDTO {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
}
