package cn.javaboy.admin.common.domain.sys.entity;

import cn.javaboy.admin.common.domain.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 岗位
 *
 * @author zzr
 */
@Data
@TableName("sys_position")
public class PositionEntity extends BaseEntity {

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 岗位描述
     */
    private String remark;

}
