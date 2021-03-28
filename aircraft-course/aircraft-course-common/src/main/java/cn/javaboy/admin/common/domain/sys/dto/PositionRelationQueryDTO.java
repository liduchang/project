package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位关系
 *
 * @author zzr
 */
@Data
public class PositionRelationQueryDTO {

    @ApiModelProperty("岗位ID")
    private Long positionId;

    @ApiModelProperty("员工ID")
    private Long userId;

}
