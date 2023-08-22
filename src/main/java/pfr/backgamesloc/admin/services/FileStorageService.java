package pfr.backgamesloc.admin.services;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path root = Paths.get("uploads");

    public void init() throws IOException {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new IOException("Could not initialize folder for upload !");
        }
    }

    public void save(MultipartFile file) throws IOException {
        try {
            if (file.getOriginalFilename() != null) {
                Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
            }
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new FileAlreadyExistsException("A file of that name already exists");
            }
            throw new IOException(e.getMessage());
        }
    }

    public UrlResource load(String fileName) throws MalformedURLException, EOFException {
        try {
            Path file = root.resolve(fileName);
            UrlResource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new EOFException("Could not read the file");
            }

        } catch (MalformedURLException e) {
            throw new MalformedURLException("Error :" + e.getMessage());
        } catch (EOFException e) {
            throw new EOFException(e.getMessage());
        }
    }
}
