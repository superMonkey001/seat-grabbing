package cn.hncj.grabbing.exception;

/**
 * @Author FanJian
 * @Date 2022/10/4
 */

public enum CustomizeExceptionEnum {
    SUCCESS(200,"成功"),
    SERVER_EXCEPTION(500,"服务器异常"),
    MOBILE_OR_PASSWORD_ERROR(1000,"手机号或密码错误"),
    MOBILE_IS_NOT_VALID(1001,"手机号有误"),
    EMPTY_STOCK(1002,"座位数量不足"),
    REPEAT_ERROR(1003,"重复选座");
    private int code;
    private String msg;

    CustomizeExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
