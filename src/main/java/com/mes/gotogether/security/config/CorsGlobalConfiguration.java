package com.mes.gotogether.security.config;

import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

//@Configuration
//@EnableWebFlux
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://localhost:8080")
                .allowedMethods("POST, GET, HEAD, OPTIONS")
                .allowedHeaders("X-PINGOTHER","Origin","X-Requested-With","X-HTTP-Method-Override", "Content-Type","Accept")
                .allowCredentials(true)
                .exposedHeaders("Origin","X-Requested-With","X-HTTP-Method-Override", "Content-Type","Accept")
                .maxAge(3600000);
    }

    /*@Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("http://localhost")
                .allowedMethods("POST, GET, HEAD, OPTIONS")
                .maxAge(3600);
    }*/
}
