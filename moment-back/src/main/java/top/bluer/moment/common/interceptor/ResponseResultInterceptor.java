package top.bluer.moment.common.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @program: tool-common
 * @description: 定义返回结果拦截器
 * @author: bluer
 * @date: 2021-10-15 16:01
 * codes: 扁鹊
 **/
@Slf4j
@Component
public class ResponseResultInterceptor implements HandlerInterceptor {
    /**
     * 使用统一返回体的标识
     */
    private static final String RESPONSE_RESULT_ANNOTATION = "RESPONSE-RESULT-ANNOTATION";

    /**
     * @description: 返回参数预处理
     * @date: 2021/11/27 20:24
     * codes: 扁鹊
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 正在处理请求的方法Bean
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取当前类
            final Class<?> clazz = handlerMethod.getBeanType();
            // 获取当前方法
            final Method method = handlerMethod.getMethod();
            // 判断是否在类对象上加了注解
            if (clazz.isAnnotationPresent(RestController.class)) {
                // 设置该请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, clazz.getAnnotation(RestController.class));
            }
            // 判断是否在方法上加了注解
            else if (method.isAnnotationPresent(RestController.class)) {
                // 设置该请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, method.getAnnotation(RestController.class));
            }

            // 记录操作日志
            if (method.isAnnotationPresent(ApiOperation.class)) {
                log.info((StpUtil.isLogin() ? " 账号["+StpUtil.getLoginIdAsString() + "]->" + StpUtil.getLoginDevice() : " 账号[游客]->未登录") + "->" + method.getAnnotation(ApiOperation.class).value());
            }
        }
        return true;
    }
}
