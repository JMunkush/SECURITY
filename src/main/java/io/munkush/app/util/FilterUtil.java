package io.munkush.app.util;

import io.munkush.app.config.security.JwtFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

public class FilterUtil {

    public static void checkTokenAndSetAuth(String token,
                                            JwtFactory jwtFactory,
                                            UserDetailsService userDetailsService){


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
