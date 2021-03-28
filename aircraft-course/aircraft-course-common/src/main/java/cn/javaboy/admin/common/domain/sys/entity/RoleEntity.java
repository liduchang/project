package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 角色 ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
@TableName("sys_role")
public class RoleEntity extends BaseEntity {


    private String roleName;

    private String remark;
}
