package cn.javaboy.admin.common.domain.constant;


import cn.javaboy.admin.common.domain.base.BaseEnum;

/**
 * [  ]
 *
 * @author liduchang
 * @since JDK1.8
 */
public enum DataScopeViewTypeEnum implements BaseEnum {

    ME(0,0,"本人"),

    DEPARTMENT(1,5,"本组织机构"),

    DEPARTMENT_AND_SUB(2,10,"本组织机构及下属组织机构"),

    ALL(3,15,"全部");

    private Integer value;
    private Integer level;
    private String desc;

    DataScopeViewTypeEnum(Integer value,Integer level, String desc) {
        this.value = value;
        this.level = level;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public Integer getLevel() {
        return level;
    }

    @Override
    public String getDesc() {
        return desc;
    }


}
