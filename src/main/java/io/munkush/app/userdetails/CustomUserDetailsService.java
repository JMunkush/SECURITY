package io.munkush.app.userdetails;

import io.munkush.app.model.User;
import io.munkush.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        var user = userRepository.findByUsername(username);


        return map(user);
    }


    public static CustomUserDetails map(User user){

        return new CustomUserDetails(user.getUsername(),
                user.getPassword(),
                user.getRole().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList());
    }
}
