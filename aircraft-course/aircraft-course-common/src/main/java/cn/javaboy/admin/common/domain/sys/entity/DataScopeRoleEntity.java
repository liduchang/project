package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 数据范围与角色关系 ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
@Data
@TableName("sys_role_data_scope")
public class DataScopeRoleEntity extends BaseEntity {

    /**
     * 数据范围id
     */
    private Integer dataScopeType;
    /**
     * 数据范围类型
     */
    private Integer viewType;
    /**
     * 角色id
     */
    private Long roleId;
}
