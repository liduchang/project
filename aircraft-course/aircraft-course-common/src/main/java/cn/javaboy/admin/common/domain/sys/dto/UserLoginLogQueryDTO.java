package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * [ 用户登录日志 ]
 *
 * @author yandanyang
 * @version 1.0
 */
@Data
public class UserLoginLogQueryDTO extends PageParamDTO {


    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;


    @ApiModelProperty("用户名")
    private String userName;

}
