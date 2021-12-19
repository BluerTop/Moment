package top.bluer.moment.common.common;

/**
 * @program: tool-common
 * @description: 定义API返回码枚举类
 * @author: bluer
 * @date: 2021-10-15 15:36
 * codes: 扁鹊
 **/
public enum ResultCode {
    /*成功状态码*/
    SUCCESS(200, "成功"),

    /*失败状态码*/
    NOT_FOUND(404, "请求的资源不存在"),
    INTERNAL_ERROR(500, "系统内部错误"),
    PARAMETER_EXCEPTION(501, "请求参数校验异常"),
    MultiTerminalAccountLogin(510, "账号在其他地方登录，请重新登录"),
    LoginExpired(511, "登录过期，请重新登录"),
    RedisCommandTimedOut(500, "Redis command timed out"),

    /*业务状态码*/
    USER_NOT_EXIST_ERROT(10001, "用户不存在"),
    ;

    private Integer code;
    private String message;

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

