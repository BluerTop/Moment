package top.bluer.moment.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    * @description: 解密
    * @date: 2021/11/26 16:53
    * codes: 扁鹊
    **/
    public String decrypt(String ciphertext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasypt);
        return encryptor.decrypt(ciphertext);
    }
}
