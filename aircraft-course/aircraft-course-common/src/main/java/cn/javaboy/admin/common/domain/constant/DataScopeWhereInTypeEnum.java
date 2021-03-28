package cn.javaboy.admin.common.domain.constant;


import cn.javaboy.admin.common.domain.base.BaseEnum;

/**
 * [  ]
 *
 * @author liduchang
 * @version 1.0
 * @since JDK1.8
 */
public enum DataScopeWhereInTypeEnum implements BaseEnum {

    EMPLOYEE(0,"以员工IN"),

    DEPARTMENT(1,"以部门IN"),

    CUSTOM_STRATEGY(2,"自定义策略");

    private Integer value;
    private String desc;

    DataScopeWhereInTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
