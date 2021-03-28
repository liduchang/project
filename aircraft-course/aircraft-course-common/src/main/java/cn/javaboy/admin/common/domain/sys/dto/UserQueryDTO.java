package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 员工列表DTO
 *
 * @author lidoudou
 * @date 2017年12月21日上午09:09:31
 */
@Data
public class UserQueryDTO extends PageParamDTO {

    private String phone;

    private String actualName;

    private String keyword;

    private Long departmentId;

    private Integer isLeave;

    private Integer isDisabled;

    /**
     * 删除状态 0否 1是
     */
    @ApiModelProperty("删除状态 0否 1是 不需要传")
    private Integer isDelete;

    @ApiModelProperty("员工id集合")
    private List<Long> userIds;

}
