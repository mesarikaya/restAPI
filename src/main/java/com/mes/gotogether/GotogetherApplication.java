package com.mes.gotogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GotogetherApplication {

    // private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(GotogetherApplication.class, args);
        System.out.println("****Run Successful ****");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /*@Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {

            NomatimOpenStreetMapQuery[] queryResult = restTemplate.getForObject(
                    "https://nominatim.openstreetmap.org/search/parklaan 103,sassenheim?format=json&addressdetails=1&limit=1",
                    NomatimOpenStreetMapQuery[].class);
            log.info("**** QUERY ARRAY LENGTH: " + queryResult.length);
            log.info("******QUERY RESULT IS: " + queryResult[0].toString());
        };
    }*/

}
