package pfr.backgamesloc.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private ConfigurableBeanFactory beanFactory;

    @Bean("securityFilterChain")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var chain = http
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/csrf").permitAll()
                        .requestMatchers("/auth/**").authenticated()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/customer/**").authenticated()
                        .anyRequest().permitAll()
                ).csrf().disable()
                .rememberMe(customizer -> customizer.alwaysRemember(true).key("demo"))
                .build();

        var rememberMeServices = http.getSharedObject(RememberMeServices.class);
        beanFactory.registerSingleton("rememberMeServices", rememberMeServices);

        return chain;
    }
}
