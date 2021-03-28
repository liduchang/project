package cn.javaboy.admin.common.domain.sys.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
@Data
public class SysParamQueryDTO extends PageParamDTO {

    @ApiModelProperty("参数KEY")
    private String key;

    @ApiModelProperty("参数类别")
    private String configGroup;


}
