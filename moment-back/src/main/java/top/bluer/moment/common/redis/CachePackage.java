package top.bluer.moment.common.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.time.Duration;

/**
 * @program: moment-back
 * @description: Cache（缓存） 封装
 * @author: bluer
 * @date: 2021-11-26 15:59
 * codes: 扁鹊
 **/
@Getter
@ToString
@Builder
public class CachePackage {

    // 验证码 key 模板
    public static final String SMS_CAPTCHA = "moment:sms::captcha:${phone}-${describe}";
    // 验证码时效 天 key 模板
    public static final String SMS_CAPTCHA_DAY = "moment:sms::captcha:day:${phone}-${describe}";
    // 验证码时效 小时 key 模板
    public static final String SMS_CAPTCHA_HOUR = "moment:sms::captcha:hour:${phone}-${describe}";
    // 账户信息 key 模板
    public static final String ACCOUNT_INFO = "moment:info::account:${account}";
    // 用户信息 key 模板
    public static final String USER_INFO = "moment:info::userId:${userId}";

    // 随机图片 key 模板
    public static final String IMG_LIST_DATE = "moment:image::photos:${date}-${index}";

    /**
     * redis key
     */
    @NonNull
    private final String key;
    /**
     * redis value
     */
    @NonNull
    private final Object value;
    /**
     * 超时时间 秒
     */
    @Nullable
    private Duration expire;

    public CachePackage(String key, Object value, Duration expire) {
        this.key = key;
        this.value = value;
        this.expire = expire;
    }
}
