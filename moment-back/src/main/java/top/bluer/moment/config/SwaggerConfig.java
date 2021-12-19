package top.bluer.moment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @program: moment-back
 * @description: Swagger配置类
 * @author: bluer
 * @date: 2021-11-27 12:16
 * codes: 扁鹊
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.bluer.moment.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("片刻Swagger文档")
                        .description("片刻Swagger文档...")
                        .version("0.0.1")
                        .contact(new Contact("片刻", "https://time.bluer.top", "blue9412@aliyun.com"))
                        .license("The Apache License")
                        .licenseUrl("https://time.bluer.top")
                        .build());
    }
}
