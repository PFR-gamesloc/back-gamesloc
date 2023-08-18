package pfr.backgamesloc;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pfr.backgamesloc.admin.services.FileStorageService;

@SpringBootApplication
@RequiredArgsConstructor
public class BackGameslocApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackGameslocApplication.class, args);
    }


}
