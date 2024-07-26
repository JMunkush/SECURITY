package io.munkush.app.controller;

import io.munkush.app.config.security.JwtFactory;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final JwtFactory jwtFactory;

    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    @GetMapping("/successLogin")
    public String successLogin(UsernamePasswordAuthenticationToken token,
                               HttpServletResponse httpServletResponse){


        String accessToken = jwtFactory.createAccess(token.getName());
        String refreshToken = jwtFactory.createRefresh(token.getName());

        Cookie accessCookie = new Cookie("access-token", accessToken);
        accessCookie.setPath(UUID.randomUUID().toString());
        accessCookie.setHttpOnly(true);
        accessCookie.setMaxAge(70000000);

        Cookie refreshCookie = new Cookie("refresh-token", refreshToken);
        refreshCookie.setPath(UUID.randomUUID().toString());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(70000000);


        /* ДОДЕЛАТЬ*/
        httpServletResponse.addCookie(accessCookie);
        httpServletResponse.addCookie(refreshCookie);

        return "Successfully authenticated";
    }

}
