package com.epam.fitness.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@ComponentScan("com.epam.fitness")
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String STYLES_MATCHER = "/styles/**";
    private static final String LOGIN_PAGE_URL = "/login";
    private static final String LOGIN_PROCESSING_URL = "/doLogin";
    private static final String HOME_PAGE_URL = "/home";
    private static final String LOGIN_FAILURE_URL = "/login?login_fail=true";
    private static final String USERNAME_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String LOG_OUT_URL = "/logOut";

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MessageDigestPasswordEncoder("MD5");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(STYLES_MATCHER)
                .permitAll()
                .anyRequest()
                .permitAll()
                .and()

                .formLogin()
                .loginPage(LOGIN_PAGE_URL)
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .defaultSuccessUrl(HOME_PAGE_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .usernameParameter(USERNAME_PARAMETER)
                .passwordParameter(PASSWORD_PARAMETER)
                .permitAll()
                .and()

                .logout()
                .permitAll()
                .logoutUrl(LOG_OUT_URL)
                .logoutSuccessUrl(LOGIN_PAGE_URL)
                .invalidateHttpSession(false);
    }

}
