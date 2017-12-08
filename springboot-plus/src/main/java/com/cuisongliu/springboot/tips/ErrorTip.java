package com.cuisongliu.springboot.tips;

/**
 * 返回给前台的错误提示
 *
 * @date 2016年11月12日 下午5:05:22
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
}
