package top.bluer.moment.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.common.redis.CachePackage;
import top.bluer.moment.common.redis.MomentRedis;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @program: moment-back
 * @description: Jasypt加密工具类
 * @author: bluer
 * @date: 2021-11-26 16:52
 * codes: 扁鹊
 **/
@Component
public class JasyptUtil {
    @Value("${jasypt.encryptor.password}")
    String jasypt;

    @Resource
    private MomentRedis momentRedis;

    /**
    * @description: 加密
    * @date: 2021/11/26 16:53
    * codes: 扁鹊
    **/
    public String encrypt(String text) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasypt);
        return encryptor.encrypt(text);
    }

    /**
    * @description: 加密 返回UUID
    * @date: 2021/11/26 16:53
    * codes: 扁鹊
    **/
    public String encryptUid(String text) {
        String uuid;
        while (true){
            uuid = UuidUtil.beltUUID();
            String o = momentRedis.get(uuid);
            if (Objects.isNull(o)){
                momentRedis.set(CachePackage.builder().key(uuid).value(text).expire(Duration.ofMinutes(30L)).build());
                break;
            }
        }
        return uuid;
    }

    /**
    * @description: 解密
    * @date: 2021/11/26 16:53
    * codes: 扁鹊
    **/
    public String decrypt(String ciphertext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasypt);
        return encryptor.decrypt(ciphertext);
    }

    /**
    * @description: 解密 UUID
    * @date: 2021/11/26 16:53
    * codes: 扁鹊
    **/
    public String decryptUid(String ciphertext) {
        String o = momentRedis.get(ciphertext);
        if (Objects.isNull(o)){
            throw new BizException("刷新后再试");
        }
        return o;
    }
}
