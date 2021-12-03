package com.atguigu.srb.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

/**
 * @Author ozq
 * @Date 2021/11/9 7:45
 * @Description
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket apiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台管理")
                .apiInfo(adminApiInfo())
                .select()//选择器
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build()
                ;
    }

    @Bean
    public Docket webConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("网站api接口")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build()
                ;

    }

    @Bean
    public ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("尚融宝网站接口Api文档")
                .description("描述尚融宝网站各种接口的调用")
                .build()
                ;
    }

    @Bean
    public ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("尚融宝后台管理Api文档")
                .description("描述尚融宝后台各种接口的调用")
                .build()
                ;
    }

}
