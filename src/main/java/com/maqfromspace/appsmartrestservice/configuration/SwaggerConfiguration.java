package com.maqfromspace.appsmartrestservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger configuration
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${app.version}")
    String projectVersion;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.maqfromspace.appsmartrestservice.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .tags(new Tag("Authentication", "Authentication controller"),
                        new Tag("Customers", "Customers controller"),
                        new Tag("Products", "Products controller"));
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Customer products Service Api")
                .description("OpenApi description for \" Customer products\" service")
                .contact(new Contact("Leonov Maksim", "https://github.com/maqfromspace", "maqfromspace@gmail.com"))
                .version(projectVersion)
                .build();
    }
    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }
}