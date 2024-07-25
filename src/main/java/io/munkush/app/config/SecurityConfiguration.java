package io.munkush.app.config;

import io.munkush.app.config.jwt.JwtFactory;
import io.munkush.app.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFactory jwtFactory;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {


        http.csrf(AbstractHttpConfigurer::disable);


        http.authorizeHttpRequests(r -> {
            r.requestMatchers("/api/v1/users").permitAll();
            r.anyRequest().authenticated();
        });


        http.addFilterAfter(new JwtTokenFilter(jwtFactory, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
