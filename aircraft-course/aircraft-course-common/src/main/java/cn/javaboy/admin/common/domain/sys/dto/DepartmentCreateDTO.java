package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @date
 */
@Data
public class DepartmentCreateDTO {


    @ApiModelProperty("部门名称")
    @Length(min = 1, max = 50, message = "请输入正确的部门名称(1-50个字符)")
    @NotNull(message = "请输入正确的部门名称(1-50个字符)")
    private String name;

    @ApiModelProperty("部门简称")
    private String shortName;

    @ApiModelProperty("部门负责人id")
    private Long managerId;

    @ApiModelProperty("上级部门id (可选)")
    private Long parentId;

}
