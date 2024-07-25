package io.munkush.app.filter;

import io.munkush.app.config.jwt.JwtFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtFactory jwtFactory;
    private final UserDetailsService userDetailsService;

    public JwtTokenFilter(JwtFactory jwtFactory, UserDetailsService userDetailsService) {
        this.jwtFactory = jwtFactory;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

        var token = request.getHeader("Authorization");

        // Если токена существует, то:
        if(token != null) {
            if(token.startsWith("Bearer") && token.length() > 7){
                token = token.substring(7);

                var tokenExpired = jwtFactory.isTokenExpired(token);

                if (!token.isBlank() && !tokenExpired) {
                    var username = jwtFactory.getUsername(token);
                    var userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), null);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        }

        filterChain.doFilter(request, response);

    }
}
