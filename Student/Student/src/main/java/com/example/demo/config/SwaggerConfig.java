//package com.example.demo.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
//
//@Configuration
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/swagger-ui/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
//                .resourceChain(false)
//                .addTransformer(new CssLinkResourceTransformer());
//        registry.addResourceHandler("/swagger-resources/**")
//                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/");
//        registry.addResourceHandler("/v3/api-docs/**")
//                .addResourceLocations("classpath:/META-INF/resources/");
//    }
//}
//
