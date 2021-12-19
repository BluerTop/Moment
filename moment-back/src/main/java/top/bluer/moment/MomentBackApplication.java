package top.bluer.moment;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: moment-back
 * @description: 片刻-后端服务
 * @date: 2021-11-25 11:37
 * @version: 0.0.1
 * codes: 扁鹊
 **/
@SpringBootApplication
@MapperScan({"top.bluer.moment.mapper"})
@Slf4j
public class MomentBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(MomentBackApplication.class, args);
        log.info("片刻后端服务启动成功...");
    }
}
