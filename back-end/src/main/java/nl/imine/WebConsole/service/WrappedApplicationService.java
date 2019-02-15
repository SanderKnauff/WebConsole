package nl.imine.WebConsole.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.imine.WebConsole.model.WrappedApplication;
import nl.imine.WebConsole.repository.WrappedApplicationRepository;
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

    private final WrappedApplicationRepository wrappedApplicationRepository;

    public WrappedApplicationService(WrappedApplicationRepository wrappedApplicationRepository) {
        this.wrappedApplicationRepository = wrappedApplicationRepository;
    }

    public void save(WrappedApplication wrappedApplication) {
        wrappedApplicationRepository.save(wrappedApplication);
    }

    public Optional<WrappedApplication> findById(String id) {
        return wrappedApplicationRepository.findById(id);
    }

    public List<WrappedApplication> getAll() {
        return wrappedApplicationRepository.findAll();
    }

}
