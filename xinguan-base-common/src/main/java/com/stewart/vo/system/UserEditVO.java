package com.stewart.vo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @author Stewart
 * @create 2021/1/19
 */
@Data
public class UserEditVO {
    private Long id;

    private String avatar;

    private String username;

    private String nickname;

    private String email;

    private String phoneNumber;

    private Integer sex;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birth;

    private Long departmentId;
}
