package top.bluer.moment;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MomentBackApplicationTests {

    @Value("${jasypt.encryptor.password}")
    String jasypt;

    @Test
    void jasyptTest() {
        String text = "";
        String ec = jasyptEncrypt(text);
        System.out.println("加密内容:" + text + ", 密钥:" + "");
        System.out.println("加密后:" + ec);
        String text2 = jasyptDecrypt(ec);
        System.out.println("解密内容：" + text2);
    }

    public String jasyptEncrypt(String text) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasypt);
        return encryptor.encrypt(text);
    }

    public String jasyptDecrypt(String ciphertext) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(jasypt);
        return encryptor.decrypt(ciphertext);
    }
}
