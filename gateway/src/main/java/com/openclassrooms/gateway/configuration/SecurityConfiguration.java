package com.openclassrooms.gateway.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfiguration {
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                //.cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchange -> exchange
                .pathMatchers("/login").permitAll()
                .pathMatchers("/patient/**").hasRole("USER")
                .pathMatchers("/report/**").hasRole("USER")
                .pathMatchers("/risk-analyser/**").hasRole("USER")
        ).httpBasic(Customizer.withDefaults()).build();
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager(){
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsRepository());
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsRepository() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails hubert = userBuilder.username("accountUI").password(">VCcO8`4]14jRM26g1").roles("USER").build();
        UserDetails jeanFrancois = userBuilder.username("accountRiskAnalyser").password("z{`aeKwgU!$]+h-YD3B").roles("USER").build();
        return new MapReactiveUserDetailsService(hubert, jeanFrancois);
    }
}
