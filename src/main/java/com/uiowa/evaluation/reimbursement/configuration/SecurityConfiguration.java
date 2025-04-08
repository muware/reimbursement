package com.uiowa.evaluation.reimbursement.configuration;

import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This is where security (or lack thereof, yikes) is configured.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final String h2ConsolePath;

    public SecurityConfiguration(
        Environment environment,
        H2ConsoleProperties h2ConsoleProperties
    ) {
        this.h2ConsolePath = 
            environment
            .getProperty(
                "spring.h2.console.path", 
                h2ConsoleProperties.getPath()
            );
    }

    /**
     * Disable security for all requests.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                c -> c.anyRequest().permitAll()
            )
            .csrf(c -> c.disable());

        return http.build();
    }

    /**
     * Disable security for the H2 Console.
     * H2 console is available at http://localhost:8080/h2-console
     * Just click on "Connect" without entering a password.
     */
    @Bean
    public WebSecurityCustomizer disableSecurityForH2Console() {
        String pattern = h2ConsolePath + "/**";
        return web -> web.ignoring().requestMatchers(new AntPathRequestMatcher(pattern));
    }
}
