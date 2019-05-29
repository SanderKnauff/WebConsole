package nl.imine.webconsole.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ApplicationIconService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationIconService.class);

    private final Path iconFolder;

    public ApplicationIconService(@Value("${application.icon.folder}") String iconFolder) {
        this.iconFolder = Paths.get(iconFolder);
    }

    public Optional<byte[]> getIcon(String icon) {
        prepareDirectory();
        Path iconFile = iconFolder.resolve(icon);
        if (Files.exists(iconFile)) {
            try {
                return Optional.of(Files.readAllBytes(iconFile));
            } catch (IOException e) {
                logger.error("Exception while reading icon ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        }
        return Optional.empty();
    }

    public void saveIcon(String icon, byte[] data) {
        prepareDirectory();
        Path iconFile = iconFolder.resolve(icon);
        try {
            Files.write(iconFile, data);
        } catch (IOException e) {
            logger.error("Exception while saving icon ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    private void prepareDirectory() {
        try {
            Files.createDirectories(iconFolder);
        } catch (IOException e) {
            logger.error("Exception while creating directories ({}: {})", e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
