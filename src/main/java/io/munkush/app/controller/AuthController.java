package io.munkush.app.controller;

import io.munkush.app.config.security.JwtFactory;
import io.munkush.app.dto.LoginRequestDto;
import io.munkush.app.dto.LoginResponseDto;
import io.munkush.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final JwtFactory jwtFactory;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto){


        var userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());


        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(),
                        requestDto.getPassword(), userDetails.getAuthorities());


        var authentication = authenticationManager.authenticate(authenticationToken);

        if(authentication.isAuthenticated()){
            var accessToken = jwtFactory.createAccess(requestDto.getUsername());
            var refreshToken = jwtFactory.createRefresh(requestDto.getUsername());


            return ResponseEntity.ok(LoginResponseDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .date(LocalDateTime.now())
                    .build());
        } else {
            throw new IllegalStateException("not authenticated");
        }



    }

}
