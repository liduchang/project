package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * [  ]
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
@Data
@TableName("sys_resource")
public class ResourceEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3848408566432915214L;

    /**
     * 功能权限类型：1.模块 2.页面 3.功能点 4.子模块
     */
    private Integer type;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由name 英文关键字
     */
    @TableField(value = "`key`")
    private String key;


    private String url;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 父级key
     */
    private String parentKey;

 
}
