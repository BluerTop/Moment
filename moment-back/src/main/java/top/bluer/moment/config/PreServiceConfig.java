package top.bluer.moment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @program: moment-back
 * @description: 服务启动前配置
 * @author: bluer
 * @date: 2021-11-25 11:50
 * codes: 扁鹊
 **/
@Configuration
@Slf4j
public class PreServiceConfig implements BeanPostProcessor {

    /**
     * @description: 加载密钥jasypt
     * @date: 2021/11/25 12:05
     * codes: 扁鹊
     **/
    @PostConstruct
    public void loadKeyJasyptInit() {
        try {
            File file = ResourceUtils.getFile("classpath:jasypt.txt");
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = br.readLine();
                System.setProperty("jasypt.encryptor.password", lineTxt);
                log.info("加载密钥jasypt成功...");
                br.close();
            } else {
                log.error("加载密钥jasypt失败");
            }
        } catch (Exception e) {
            log.error("加载密钥jasypt失败-无法连接到服务器");
        }
    }
}
