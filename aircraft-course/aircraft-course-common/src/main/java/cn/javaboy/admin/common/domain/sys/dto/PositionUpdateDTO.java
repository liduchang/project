package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位
 *
 * @author zzr
 */
@Data
public class PositionUpdateDTO extends PositionAddDTO {

    @ApiModelProperty("主键")
    private Long id;
}
