package com.stewart.vo;

import lombok.Data;

/**
 * @author Stewart
 * @create 2021/1/3
 */
@Data
public class UserVO {
    private String username;

    private String nickname;

    private String email;

    private Integer sex;

    private Long departmentId;
}
