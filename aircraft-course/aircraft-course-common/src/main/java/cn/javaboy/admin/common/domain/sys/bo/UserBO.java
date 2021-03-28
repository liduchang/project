package cn.javaboy.admin.common.domain.sys.bo;

import cn.javaboy.admin.common.domain.sys.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserBO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 员工名称
     */
    private String actualName;

    /**
     * 别名
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 部门id
     */
    private Long departmentId;

    /**
     * 是否离职
     */
    private Integer isLeave;

    /**
     * 是否被禁用
     */
    private Integer isDisabled;

    /**
     * 删除状态 0否 1是
     */
    private Long isDelete;

    /**
     * 是否为超级管理员
     */
    private Boolean isSuperman;

    public UserBO(UserEntity userEntity, boolean isSuperman) {
        this.id = userEntity.getId();
        this.loginName = userEntity.getLoginName();
        this.actualName = userEntity.getActualName();
        this.nickName = userEntity.getNickName();
        this.phone = userEntity.getPhone();
        this.departmentId = userEntity.getDepartmentId();
        this.isLeave = userEntity.getIsLeave();
        this.isDisabled = userEntity.getIsDisabled();
        this.isDelete = userEntity.getIsDelete();
        this.isSuperman = isSuperman;
    }

}
