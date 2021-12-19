package top.bluer.moment.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.bluer.moment.common.redis.CachePackage;
import top.bluer.moment.common.redis.MomentRedis;
import top.bluer.moment.entity.MomentAccount;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.service.MomentAccountService;
import top.bluer.moment.service.MomentUserService;
import top.bluer.moment.utils.TemplateUtil;

import javax.annotation.Resource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

import static top.bluer.moment.common.redis.CachePackage.ACCOUNT_INFO;
import static top.bluer.moment.common.redis.CachePackage.USER_INFO;

/**
 * @program: moment-back
 * @description: Sa-Token 配置类
 * @author: bluer
 * @date: 2021-11-27 19:40
 * codes: 扁鹊
 **/
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Resource
    private MomentAccountService momentAccountService;

    @Resource
    private MomentUserService momentUserService;

    @Resource
    private MomentRedis momentRedis;

    /**
     * @description: 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     * @date: 2021/11/28 11:11
     * codes: 扁鹊
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册注解拦截器，并排除不需要注解鉴权的接口地址 (与登录拦截器无关)
        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }

    /**
     * @description: 获取当前会话的账户信息
     * @date: 2021/11/27 22:14
     * codes: 扁鹊
     **/
    public MomentAccount getAccount() {
        String loginId = StpUtil.getLoginIdAsString();
        String loginDevice = StpUtil.getLoginDevice();
//        String accountInfo = TemplateUtil.processTemplate(ACCOUNT_INFO, new HashMap<String, Object>(1) {{
//            put("account", loginId);
//        }});
//        MomentAccount momentAccount = momentRedis.get(accountInfo);
//        if (!Objects.isNull(momentAccount)) {
//            return momentAccount;
//        }
//        momentAccount = MomentAccount.builder().accountPhone(loginDevice.equals("P") ? loginId : null).accountMail(loginDevice.equals("M") ? loginId : null).build();
//        momentAccount = momentAccountService.queryByCondition(momentAccount);
//        momentRedis.set(CachePackage.builder().key(accountInfo).value(momentAccount).expire(Duration.ofMinutes(30L)).build());
        MomentAccount momentAccount = MomentAccount.builder().accountPhone(loginDevice.equals("P") ? loginId : null).accountMail(loginDevice.equals("M") ? loginId : null).build();
        momentAccount = momentAccountService.queryByCondition(momentAccount);
        return momentAccount;
    }

    /**
     * @description: 获取当前会话的用户信息
     * @date: 2021/11/27 22:14
     * codes: 扁鹊
     **/
    public MomentUser getUser() {
        Integer userId = getAccount().getUserId();
//        String userInfo = TemplateUtil.processTemplate(USER_INFO, new HashMap<String, Object>(1) {{
//            put("userId", userId);
//        }});
//        MomentUser momentUser = momentRedis.get(userInfo);
//        if (!Objects.isNull(momentUser)){
//            return momentUser;
//        }
//        momentRedis.set(CachePackage.builder().key(userInfo).value(momentUser).expire(Duration.ofMinutes(30L)).build());
        return momentUserService.queryById(userId);
    }
}
