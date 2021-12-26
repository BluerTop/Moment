package top.bluer.moment.common.handler;

import cn.dev33.satoken.exception.NotLoginException;
import io.lettuce.core.RedisCommandTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import top.bluer.moment.common.common.ErrorResult;
import top.bluer.moment.common.exception.BaseException;
import top.bluer.moment.common.exception.BizException;

import java.util.List;

import static top.bluer.moment.common.common.ResultCode.*;

/**
 * @program: tool-common
 * @description: 定义全局异常处理类
 * @author: bluer
 * @date: 2021-10-15 16:47
 * codes: 扁鹊
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 统一处理自定义基础异常
     */
    @ExceptionHandler(BaseException.class)
    public ErrorResult baseException(BaseException e) {
        if (StringUtils.isEmpty(e.getCode())) {
            return ErrorResult.error(e.getMessage());
        }
        return ErrorResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 统一处理自定义业务异常
     */
    @ExceptionHandler(BizException.class)
    public ErrorResult bizException(BizException e) {
        if (StringUtils.isEmpty(e.getCode())) {
            return ErrorResult.error(e.getMessage());
        }
        return ErrorResult.error(e.getCode(), e.getMessage());
    }

    /**
     * QueryTimeoutException
     */
    @ExceptionHandler(QueryTimeoutException.class)
    public ErrorResult queryTimeoutException(QueryTimeoutException e) {
        log.error(e.getMessage());
        return ErrorResult.error(RedisCommandTimedOut);
    }

    /**
     * QueryTimeoutException
     */
    @ExceptionHandler(RedisCommandTimeoutException.class)
    public ErrorResult queryTimeoutException(RedisCommandTimeoutException e) {
        log.error(e.getMessage());
        return ErrorResult.error(RedisCommandTimedOut);
    }

    /**
     * 未登录异常处理
     */
    @ExceptionHandler(NotLoginException.class)
    public ErrorResult bizException(NotLoginException e) {
        String message = e.getMessage();
        if (message.contains("Token无效")) {
            return ErrorResult.error(LoginExpired);
        }
        if (message.contains("Token已被顶下线")) {
            return ErrorResult.error(MultiTerminalAccountLogin);
        }
        return ErrorResult.error(500, message);
    }

    /**
     * 统一处理缺少 Servlet 请求部分异常
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ErrorResult handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(PARAMETER_EXCEPTION);
    }

    /**
     * 统一处理非自定义异常外的所有异常
     */
    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(e.getMessage());
    }

    /**
     * 兼容Validation校验框架：忽略参数异常处理器
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResult parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(PARAMETER_EXCEPTION, "请求参数 " + e.getParameterName() + " 不能为空");
    }

    /**
     * 兼容Validation校验框架：缺少请求体异常处理器
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult parameterBodyMissingExceptionHandler(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return ErrorResult.error(PARAMETER_EXCEPTION, "参数体不能为空");
    }

    /**
     * 兼容Validation校验框架：参数效验异常处理器
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        // 获取异常信息
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return ErrorResult.error(PARAMETER_EXCEPTION, fieldError.getDefaultMessage());
            }
        }
        return ErrorResult.error(PARAMETER_EXCEPTION, "请求参数校验异常");
    }
}
