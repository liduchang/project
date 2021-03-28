package cn.javaboy.admin.common.domain.sys.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 修改密码所需参数
 *
 * @author cyj
 * @date 2018-02-23 下午 4:53
 */
@Data
public class UserUpdatePwdDTO {

    @ApiModelProperty("新密码")
    @NotNull
    private String pwd;

    @ApiModelProperty("原密码")
    @NotNull
    private String oldPwd;

}
