package cn.hncj.grabbing.vo;

import cn.hncj.grabbing.exception.CustomizeExceptionEnum;
import lombok.Data;

/**
 * @Author FanJian
 * @Date 2022/10/4
 */
@Data
public class CommonResult<T> {
    private int code;
    private String msg;
    private T data;

    public CommonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult success(T data) {
        return new CommonResult(200,"成功",data);
    }

    public static CommonResult success() {
        return success(null);
    }

    public static <T> CommonResult  error(CustomizeExceptionEnum anEnum, T data) {
        return new CommonResult<>(anEnum.getCode(), anEnum.getMsg(), data);
    }

    public static CommonResult error(CustomizeExceptionEnum anEnum) {
        return error(anEnum,null);
    }
}
