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
public class ResourceMenuVO {

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单Key")
    private String menuKey;

    @ApiModelProperty("菜单父级Key")
    private String parentKey;

    @ApiModelProperty("顺序")
    private Integer sort;

    @ApiModelProperty("前端路由path")
    private String url;


}
