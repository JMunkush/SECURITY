package io.munkush.app.config;

import io.munkush.app.config.security.JwtFactory;
import io.munkush.app.filter.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final JwtFactory jwtFactory;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(JwtFactory jwtFactory,
                                 @Lazy UserDetailsService userDetailsService) {
        this.jwtFactory = jwtFactory;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {


        http.csrf(AbstractHttpConfigurer::disable);


        http.authorizeHttpRequests(r -> {
            r.requestMatchers("/auth/**").permitAll();
            r.requestMatchers("/successLogin").permitAll();
            r.requestMatchers("/api/v1/users").permitAll();
            r.anyRequest().authenticated();
        });

        http.formLogin(r -> {
            r.defaultSuccessUrl("/successLogin");
        });

        http.addFilterAfter(new JwtTokenFilter(jwtFactory, userDetailsService), UsernamePasswordAuthenticationFilter.class);


        http.oauth2Client(Customizer.withDefaults());
        http.oauth2Login(Customizer.withDefaults());

        return http.build();
    }

}
