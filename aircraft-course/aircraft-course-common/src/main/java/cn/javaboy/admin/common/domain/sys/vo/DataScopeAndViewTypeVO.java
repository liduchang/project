package cn.javaboy.admin.common.domain.sys.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * [  ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
public class DataScopeAndViewTypeVO {

    @ApiModelProperty("数据范围类型")
    private Integer dataScopeType;

    @ApiModelProperty("数据范围名称")
    private String dataScopeTypeName;

    @ApiModelProperty("描述")
    private String dataScopeTypeDesc;

    @ApiModelProperty("顺序")
    private Integer dataScopeTypeSort;

    @ApiModelProperty("可见范围列表")
    private List<DataScopeViewTypeVO> viewTypeList;

}
