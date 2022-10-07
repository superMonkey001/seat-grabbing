package cn.hncj.grabbing.exception;

import cn.hncj.grabbing.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author FanJian
 * @Date 2022/10/5
 */

@ControllerAdvice
@Slf4j
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult handler(Exception ex) {
        if (ex instanceof CustomizeException) {
            CustomizeException customizeException = (CustomizeException) ex;
            return CommonResult.error(customizeException.getExceptionEnum());
        } else if (ex instanceof BindException) {
            return CommonResult.error(CustomizeExceptionEnum.MOBILE_IS_NOT_VALID);
        } else {
            log.info(ex.toString());
            return CommonResult.error(CustomizeExceptionEnum.SERVER_EXCEPTION);
        }
    }
}
