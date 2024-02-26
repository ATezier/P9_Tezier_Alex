package com.openclassrooms.medilabo.configuration;

import com.openclassrooms.medilabo.model.User;
import com.openclassrooms.medilabo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAutorities(user.getRole()));
    }

    private List<GrantedAuthority> getGrantedAutorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(role.contains(",")) {
            String[] roles = role.split(",");
            for (String _role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + _role));
            }
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities;
    }
}
