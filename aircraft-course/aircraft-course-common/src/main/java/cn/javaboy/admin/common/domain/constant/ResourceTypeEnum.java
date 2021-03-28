package cn.javaboy.admin.common.domain.constant;


import cn.javaboy.admin.common.domain.base.BaseEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * 
 * [  ]
 * 
 * @version 1.0
 * @since JDK1.8
 * @author liduchang
 * @date
 */
public enum ResourceTypeEnum implements BaseEnum {


    MENU(1,"菜单"),

    POINTS(2,"功能点");

    private Integer value;

    private String desc;

    ResourceTypeEnum(Integer value, String desc){
        this.value = value;
        this.desc = desc;
    }
    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static ResourceTypeEnum selectByValue(Integer value) {
        Optional<ResourceTypeEnum> first = Arrays.stream(ResourceTypeEnum.values()).filter(e -> e.getValue().equals(value)).findFirst();
        return !first.isPresent() ? null : first.get();
    }
}
