package pfr.backgamesloc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pfr.backgamesloc.security.config.KeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(KeyProperties.class)
public class BackGameslocApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackGameslocApplication.class, args);
    }

}
