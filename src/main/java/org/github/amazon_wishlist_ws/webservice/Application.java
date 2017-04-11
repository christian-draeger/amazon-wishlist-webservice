package org.github.amazon_wishlist_ws.webservice;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by christian.draeger on 25.04.16.
 */
@SpringBootApplication(scanBasePackages = "org.github.amazon_wishlist_ws")
@EnableSwagger2
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(regex("/wishlist.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Amazon WishList REST-Service")
                .description("fetch data from public or shared amazon wish lists and responds in json format")
                .contact("Christian Dräger")
                .termsOfServiceUrl("https://christian-draeger.github.io/amazon-wishlist-webservice/")
                .version("0.1.4")
                .build();
    }
}