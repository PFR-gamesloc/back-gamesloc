package pfr.backgamesloc.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pfr.backgamesloc.admin.services.FileStorageService;
import pfr.backgamesloc.customers.services.CustomerService;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class EnvironmentConfig {

    private final PasswordEncoder passwordEncoder;

    private final CustomerService customerService;

    private final FileStorageService fileStorageService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public void fileStorageInit() throws IOException {
        fileStorageService.init();
    }

}
