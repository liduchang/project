package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 员工关系]
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
@TableName("sys_role_user")
public class RoleUserEntity extends BaseEntity {

    private Long roleId;

    private Long userId;
}
