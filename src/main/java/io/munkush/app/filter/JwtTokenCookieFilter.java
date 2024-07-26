package io.munkush.app.filter;

import io.munkush.app.config.security.JwtFactory;
import io.munkush.app.util.FilterUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

public class JwtTokenCookieFilter extends OncePerRequestFilter {

    private final JwtFactory jwtFactory;
    private final UserDetailsService userDetailsService;

    public JwtTokenCookieFilter(JwtFactory jwtFactory, UserDetailsService userDetailsService) {
        this.jwtFactory = jwtFactory;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {

        var cookies = request.getCookies();

        var cookie = Arrays.stream(cookies)
                .filter(r -> r.getName().equals("access-token")).toList().get(0);

        if(cookie != null) {
            var token = cookie.getValue();
            // Если токена существует, то:
            FilterUtil.checkTokenAndSetAuth(token, jwtFactory, userDetailsService);
        }

        filterChain.doFilter(request, response);

    }
}
