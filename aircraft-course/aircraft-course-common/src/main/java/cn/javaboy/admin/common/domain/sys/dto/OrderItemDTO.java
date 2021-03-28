package cn.javaboy.admin.common.domain.sys.dto;

import lombok.Data;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:16
 */
@Data
public class OrderItemDTO {
    private String column;
    private boolean asc = true;
}
