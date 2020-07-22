package cn.ffcs.buffet.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * @Description: swagger工具配置类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/7/20 20:30
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * springfox为我们提供了一个Docket（摘要的意思）类
     * 我们需要把它做成一个Bean注入到spring中
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.ffcs.buffet.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * springfox允许我们将信息组合成一个ApiInfo的类，作为构造参数传给Docket
     * （当然也可以不构造这个类，而直接使用null，但是你的这个API就太low了）。
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Buffet System的接口文档")
                .description("接口文档")
                .contact("ffcs")
                .version("1.0")
                .build();
    }
}
