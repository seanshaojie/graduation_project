package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /*Swagger是为了方便进行测试后台的restful形式的接口，实现动态的更新，
    当我们在后台的接口修改了后，swagger可以实现自动的更新，而不需要认为的维护这个接口进行测试*/
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("swagger接口文档")
                //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
                .apiInfo(new ApiInfoBuilder().title("swagger接口文档")
                        //contact 创建人
                        .contact(new Contact("csj", "", "Seanshaojie@163.com")).version("1.0").build())
                .select().paths(PathSelectors.any()).build();
    }
}
