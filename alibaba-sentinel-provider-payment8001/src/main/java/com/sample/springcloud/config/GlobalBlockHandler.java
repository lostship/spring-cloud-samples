package com.sample.springcloud.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sample.springcloud.entity.CommonResult;

/**
 * 自定义Sentinel全局退路方法
 * 
 * 退路方法的返回类型，可以与被保护方法的返回类型一致，或是其子类，但不能是超类（比如Object）
 */
public class GlobalBlockHandler {

    public static CommonResult<?> handleCommonResult(BlockException e) {
        return CommonResult.fail("服务暂时无法访问").setData(e.toString());
    }

    public static String handleString(BlockException e) {
        throw new RuntimeException(e); // 抛给统一异常处理
    }

}
