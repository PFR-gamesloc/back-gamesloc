package pfr.backgamesloc.admin.services;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload !");
        }
    }

    public void save(MultipartFile file) {
        try {
            System.out.println("la");
            System.out.println(root.toAbsolutePath());
            if (file.getOriginalFilename() != null) {
                System.out.println("ici");
                System.out.println(file.getInputStream());
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists");
            }
            throw new RuntimeException(e.getMessage());
        }
    }

    public UrlResource load(String fileName) {
        try {
            Path file = root.resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file");
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException("Error :" + e.getMessage());
        }
    }
}
