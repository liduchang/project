package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 权限关系 ]
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
@Data
@TableName("sys_role_resource")
public class RoleResourceEntity extends BaseEntity {

    /**
     * 角色 id
     */
    private Long roleId;

    /**
     * 功能权限 id
     */
    private String privilegeKey;


}
