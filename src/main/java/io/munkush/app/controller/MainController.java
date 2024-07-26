package io.munkush.app.controller;

import io.munkush.app.config.security.JwtFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MainController {


    @GetMapping("/test")
    public String test(){
        return "hello";
    }


}
