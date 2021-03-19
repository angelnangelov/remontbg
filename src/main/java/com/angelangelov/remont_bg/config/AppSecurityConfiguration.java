package com.angelangelov.remont_bg.config;

import com.angelangelov.remont_bg.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AppSecurityConfiguration(
            UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/static/**","/js/**", "/css/**","/img/**").permitAll()
                .antMatchers("/index", "/", "/user/register", "/user/login","/about").anonymous()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/user/login").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userService).
                passwordEncoder(passwordEncoder);
    }
}
