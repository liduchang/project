package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_login_log")
public class UserLoginLogEntity extends BaseEntity {

    // 用户id
    private Long userId;
    // 用户名
    private String userName;
    // 用户ip
    private String remoteIp;
    // 用户端口
    private Integer remotePort;
    // 浏览器
    private String remoteBrowser;
    // 操作系统
    private String remoteOs;
    // 登录状态
    private Integer loginStatus;

}
