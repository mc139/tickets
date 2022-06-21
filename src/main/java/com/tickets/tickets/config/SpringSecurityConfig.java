package com.tickets.tickets.config;

import com.tickets.tickets.model.AppUser;
import com.tickets.tickets.model.UserDetailsServiceImpl;
import com.tickets.tickets.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.CharBuffer;
import java.util.Collections;

@Configuration
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;

    public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(
                new User("admin",
                        passwordEncoder().encode("admin"),
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/gui/**").hasAnyRole("ADMIN")
                .antMatchers("/api/**").hasAnyRole("ADMIN")
//                .antMatchers("/api/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll();

    }

    @Bean()
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeUsers() {

//        AppUser adminUser = new AppUser("admin", passwordEncoder().encode("admin").toCharArray(), "ROLE_ADMIN");
        AppUser adminUser = new AppUser("admin", passwordEncoder().encode("admin").toCharArray(), "ROLE_ADMIN");
//        AppUser normalUser = new AppUser("normal", passwordEncoder().encode(CharBuffer.wrap("admin".toCharArray()), "ROLE_USER");
        userRepository.save(adminUser);
//        userRepository.save(normalUser);
    }
}
