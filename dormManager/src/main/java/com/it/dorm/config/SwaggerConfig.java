package com.it.dorm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket initDocket(Environment env) {
        //设置要暴漏接口文档的配置环境
        //设置要显示的Swagger环境
        Profiles profile = Profiles.of("test", "dev");
        //获取项目的环境：
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = env.acceptsProfiles(profile);
        return new Docket(DocumentationType.SPRING_WEB).apiInfo(apiInfo())
                //是否启动swagger 默认为true ,如果为false，则Swagger不能再浏览器中访问
                .enable(flag).select()
                //RequestHandlerSelectors,配置要扫描接口的方式
                //.apis(RequestHandlerSelectors.any()):扫描全部
                //.apis(RequestHandlerSelectors.none()):不扫描
                //.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)):扫描类上的注解，参数是一个注解的反射对象
                //.apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class)):扫描方法上的注解
                //指定要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.it.tourRoute.controller"))
                //paths()过滤什么路径(url)
                //.paths(PathSelectors.ant("/pm/**")) 就是在localhost:8080/pm后面的路径
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //不过滤
                .paths(PathSelectors.any()).build();
        //groupName();右上角 组（有几个Docket，有几个组）
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档").description("接口文档").contact(new Contact("tourRoute", "", "123@qq.com")).version("V1.0").license("Apache 2.0").build();
    }
}

