package br.com.magiccards.shared.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfigurations {
    //http://localhost:8080/swagger-ui/index.html
    public static final String PLAYER_TAG = "Jogador";

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(metaInfot())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.magiccards.api"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(PLAYER_TAG,"End point para registro de novos jogadores"));

    }

    private ApiInfo metaInfot(){
        return new ApiInfo("Magic-Cards",
                "API para criação de lista de cartas - Jogo Magic-card.",
                "1.0",
                "Terms of Service",
                new Contact("Pedro Henrique","pedroheenri_q@hotmail.com","pedroheenriqd@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/license.html", new ArrayList<>());
    }
}
