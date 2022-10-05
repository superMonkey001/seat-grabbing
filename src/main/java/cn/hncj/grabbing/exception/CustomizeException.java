package cn.hncj.grabbing.exception;

import lombok.Data;

/**
 * @Author FanJian
 * @Date 2022/10/5
 */

@Data
public class CustomizeException extends RuntimeException{

    private CustomizeExceptionEnum exceptionEnum;

    public CustomizeException(CustomizeExceptionEnum anEnum) {
        this.exceptionEnum = anEnum;
    }
}
