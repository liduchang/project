package cn.javaboy.admin.common.domain.sys.dto;

import cn.javaboy.admin.common.domain.constant.DataScopeTypeEnum;
import cn.javaboy.admin.common.domain.constant.DataScopeWhereInTypeEnum;
import lombok.Data;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @since JDK1.8
 */
@Data
public class DataScopeSqlConfigDTO {

    /**
     * 数据范围类型
     * {@link DataScopeTypeEnum}
     */
    private DataScopeTypeEnum dataScopeType;

    /**
     * join sql 具体实现类
     */
    private Class joinSqlImplClazz;

    private String joinSql;

    private Integer whereIndex;

    /**
     * whereIn类型
     * {@link DataScopeWhereInTypeEnum}
     */
    private DataScopeWhereInTypeEnum dataScopeWhereInType;
}
