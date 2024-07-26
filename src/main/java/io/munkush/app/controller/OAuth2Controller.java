package io.munkush.app.controller;

import io.munkush.app.model.Role;
import io.munkush.app.model.User;
import io.munkush.app.repository.RoleRepository;
import io.munkush.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @GetMapping("/")
    public String oauthLogin(OAuth2AuthenticationToken token){

        User user = new User();


        user.setUsername(token.getPrincipal().getAttributes().get("login").toString());
        user.setPassword("oauth2user");

        if (roleRepository.existsByName("USER")) {
            var role = roleRepository.findByName("USER");
            role.setId(UUID.randomUUID().toString());
            user.setRole(List.of(role));
        } else {
            var role = new Role("USER");
            role.setId(UUID.randomUUID().toString());
            user.setRole(List.of(role));
        }


        userRepository.save(user);


        return "Hello" + token.getName();
    }
}
