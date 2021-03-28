package cn.javaboy.admin.common.domain.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * [  ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
public class ResourceFunctionVO {

    @ApiModelProperty("功能点名称")
    @NotBlank(message = "功能点名称不能为空")
    private String functionName;

    @ApiModelProperty("所属菜单Key")
    @NotBlank(message = "所属菜单Key不能为空")
    private String menuKey;

    @ApiModelProperty("功能点Key")
    @NotBlank(message = "功能点Key不能为空")
    private String functionKey;

    @ApiModelProperty("url列表")
    @NotEmpty(message = "url列表不能为空")
    private String url;

    @ApiModelProperty("顺序")
    private Integer sort;
}
