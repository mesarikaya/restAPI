package com.mes.gotogether.controllers.viewControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index", "/login/oauth2/callback/google"})
    public String getIndexPage(){
        System.out.println("RUNNING THIS API CALLBACK");

        log.debug("Getting Index page");
        return "index";
    }

    /*
    @GetMapping({"", "/", "/index", "/login/oauth2/callback/google"})
    public String index(Model model,
                        @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        model.addAttribute("userName", oauth2User.getName());
        model.addAttribute("clientName", authorizedClient.getClientRegistration().getClientName());
        model.addAttribute("userAttributes", oauth2User.getAttributes());
        return "index";
    }
*/

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
