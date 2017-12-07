package nl.imine.WebConsole.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.WebConsole.model.WrappedApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WrappedApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(WrappedApplicationService.class);

    private final ObjectMapper objectMapper;
    private final Path storagePath;
    private List<WrappedApplication> wrappedApplications;

    public WrappedApplicationService(ObjectMapper objectMapper, @Value("${application.list.storage.file}") String storagePath) {
        this.objectMapper = objectMapper;
        this.storagePath = Paths.get(storagePath);
    }

    public List<WrappedApplication> loadWrappedApplications() {
        if (Files.exists(storagePath)) {
            try {
                return objectMapper.readValue(Files.newInputStream(storagePath), new TypeReference<List<WrappedApplication>>() {
                });
            } catch (IOException e) {
                logger.error("Exception while loading applications from file ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        } else {
            createStorageFile(storagePath);
        }
        return new ArrayList<>();
    }


    public void saveWrappedApplications() {
        if (Files.exists(storagePath)) {
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(Files.newOutputStream(storagePath), wrappedApplications);
            } catch (IOException e) {
                logger.error("Exception while loading applications from file ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            }
        } else {
            createStorageFile(storagePath);
        }
    }

    public void addWrappedApplication(WrappedApplication wrappedApplication) {
        if(wrappedApplications == null) {
            wrappedApplications = loadWrappedApplications();
        }
        wrappedApplications.add(wrappedApplication);
        saveWrappedApplications();
    }

    public Optional<WrappedApplication> getWrappedApplicationById(String id) {
        if(wrappedApplications == null) {
            wrappedApplications = loadWrappedApplications();
        }
        return wrappedApplications.stream()
                .filter(wrappedApplication -> wrappedApplication.getId().equals(id))
                .findFirst();
    }

    public List<WrappedApplication> getAllWrappedApplications() {
        if(wrappedApplications == null) {
            wrappedApplications = loadWrappedApplications();
        }
        return wrappedApplications;
    }

    public boolean hasWrappedApplication(String id) {
        if(wrappedApplications == null) {
            wrappedApplications = loadWrappedApplications();
        }
        return wrappedApplications.stream()
                .anyMatch(wrappedApplication -> wrappedApplication.getId().equals(id));
    }


    private boolean createStorageFile(Path storagePath) {
        try {
            Path storagePathParent = storagePath.getParent();
            if(storagePathParent != null) {
                Files.createDirectories(storagePathParent);
            }
            Files.createFile(storagePath);
            return true;
        } catch (IOException e) {
            logger.error("Exception while creating applications storage file ({}: {})", e.getClass().getSimpleName(), e.getMessage());
            return false;
        }
    }
}
