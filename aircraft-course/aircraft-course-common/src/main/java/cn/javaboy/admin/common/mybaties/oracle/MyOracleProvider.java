package cn.javaboy.admin.common.mybaties.oracle;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Set;

public class MyOracleProvider extends MapperTemplate {

    public MyOracleProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String insertList(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();

        sql.append("<bind name=\"listNotEmptyCheck\" value=\"@tk.mybatis.mapper.util.OGNL@notEmptyCollectionCheck(list, '").append(ms.getId()).append(" 方法参数为空')\"/>\n");
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        // INSERT INTO TableName
        sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
        // (allColumns)
        sql.append(SqlHelper.insertColumns(entityClass, false, false, false));
        // 初始化序列名，当没有定义序列时执行报无此序列错误
        String sequenceName = "EmptySequence";
        if (entityClass.isAnnotationPresent(Sequence.class)) {
            String value = entityClass.getAnnotation(Sequence.class).value();
            if (StringUtil.isNotEmpty(value)) {
                sequenceName = value;
            }
        }
        // 获取主键信息
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(entityClass);
        boolean withoutIdentifyColumn = pkColumns.stream().noneMatch(EntityColumn::isIdentity);
        if (withoutIdentifyColumn) {
            // 无主键
            sql.append("SELECT A.*\n");
        } else {
            // 有主键，获取id需要序列自增
            sql.append("SELECT ").append(sequenceName).append(".nextval, A.*\n");
        }
        sql.append("FROM (\n");
        sql.append("<foreach collection=\"list\" item=\"record\" index=\"index\" separator=\"UNION ALL\">\n");
        sql.append("SELECT ");
        sql.append("<trim prefix=\"\" suffix=\"\" suffixOverrides=\",\">");
        for (EntityColumn column : columnList) {
            if (column.isIdentity()) continue;
            if (column.isInsertable()) {
                sql.append(column.getColumnHolder("record")).append(",");
            }
        }
        sql.append("</trim>\n");
        sql.append("FROM DUAL\n");
        sql.append("</foreach>\n");
        sql.append(") A");

        return sql.toString();
    }
}
