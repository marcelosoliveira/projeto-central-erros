package br.com.centralerrors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("br.com.centralerrors"))
                    .paths(regex("/api/v1.*"))
                    .build()
                .globalOperationParameters(Collections.singletonList(new ParameterBuilder()
                    .name("Authorization")
                    .description("Bearer token")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .defaultValue("Bearer ")
                    .required(true)
                    .build()))
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Api Central Errors By Agora Vai!")
                .description("Cadastra eventos de erros de aplicações e " +
                        "controla acesso de usuários através de usuário e senha.")
                .version("1.0")
                .contact(new Contact("Grupo Agora Vai",
                        "https://github.com/marcelosoliveira/projeto-central-erros",
                        "msbobsk8@gmail.com"))
                .license("Apache License Version 4.0")
                .licenseUrl("https://apache.org/license/LICENSE-4.0")
                .build();
    }

}
