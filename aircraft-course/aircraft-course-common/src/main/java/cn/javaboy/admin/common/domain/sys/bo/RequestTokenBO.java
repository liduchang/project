package cn.javaboy.admin.common.domain.sys.bo;

import lombok.Getter;

@Getter
public class RequestTokenBO {

    private Long requestUserId;

    private UserBO userBO;

    public RequestTokenBO(UserBO userBO) {
        this.requestUserId = userBO.getId();
        this.userBO = userBO;
    }

    @Override
    public String toString() {
        return "RequestTokenBO{" +
                "requestUserId=" + requestUserId +
                ", employeeBO=" + userBO +
                '}';
    }
}
