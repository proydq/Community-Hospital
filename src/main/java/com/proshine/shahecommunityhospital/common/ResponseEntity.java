package com.proshine.shahecommunityhospital.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口统一返回值实体类
 * @author lenovo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码常量
     */
    public interface StatusCode {
        int SUCCESS = 200;
        int FAILURE = 500;
        int UNAUTHORIZED = 401;
        int FORBIDDEN = 403;
    }

    /**
     * 接口返回值状态码，200 代表成功 500 代表失败
     * @mock 200
     */
    private int code;

    /**
     * 消息信息, success、fail
     * @mock success
     */
    private String message;

    /**
     * 接口返回的数据
     */
    private T data;

    /**
     * 成功响应
     * @param data 返回数据
     * @param <T> 数据类型
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<T> success(T data) {
        return new ResponseEntity<>(StatusCode.SUCCESS, "success", data);
    }

    /**
     * 失败响应
     * @param message 错误消息
     * @param <T> 数据类型
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<T> fail(String message) {
        return new ResponseEntity<>(StatusCode.FAILURE, message, null);
    }

    /**
     * 自定义状态码和消息的失败响应
     * @param code 状态码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return ResponseEntity
     */
    public static <T> ResponseEntity<T> fail(int code, String message) {
        return new ResponseEntity<>(code, message, null);
    }

}