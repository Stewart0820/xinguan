package com.stewart.response;

/**
 * @author Stewart
 * @create 2021/1/3
 */
public interface CustomizeResultCode {
    /**
     * 获取错误状态码
     * @return 错误状态码
     */
    Integer getCode();

    /**
     * 获取错误信息
     * @return 错误信息
     */
    String getMessage();
}
