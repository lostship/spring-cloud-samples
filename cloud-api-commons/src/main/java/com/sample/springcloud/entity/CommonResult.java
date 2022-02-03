package com.sample.springcloud.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }

    public static <T> CommonResult<T> ok() {
        return new CommonResult<>(0, "ok");
    }

    public static <T> CommonResult<T> fail(String message) {
        return new CommonResult<>(-1, message);
    }

    public CommonResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommonResult<T> setData(T data) {
        this.data = data;
        return this;
    }

}
