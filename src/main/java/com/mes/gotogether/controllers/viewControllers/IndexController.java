package com.mes.gotogether.controllers.viewControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(){

        log.debug("Getting Index page");
        return "index";
    }


    @GetMapping("/user")
    public String getUserPage(){

        log.debug("Getting USER page");
        return "user";
    }

    @GetMapping("/authenticated")
    public Mono<String> getAuthenticatedPage(){

        log.debug("Getting AUTHENTICATED page");
        return Mono .just(new String("authenticated"));
    }
}
