package top.bluer.moment.common.exception;

import java.util.Objects;

/**
 * @program: tool-common
 * @description: 自定义业务异常类
 * @author: bluer
 * @date: 2021-10-15 16:47
 * codes: 扁鹊
 **/
public class BizException extends BaseException {
    public BizException(String message) {
        super(message);
        if (Objects.isNull(message)) {
            message = "未知错误，请稍后再试";
            throw new BizException(message);
        }
    }
}