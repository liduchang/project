package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:01
 */
@Data
@TableName(value = "sys_param")
public class SysParamEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 257284726400352502L;

    /**
     * 参数key
     */
    private String configKey;

    /**
     * 参数的值
     */
    private String configValue;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数类别
     */
    private String configGroup;

    /**
     * 是否使用0 是 1否
     */
    private Integer isUsing;

    /**
     * 备注
     */
    private String remark;
}
