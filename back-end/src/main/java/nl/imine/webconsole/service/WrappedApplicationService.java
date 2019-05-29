package nl.imine.webconsole.service;

import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.repository.WrappedApplicationRepository;
import org.springframework.stereotype.Service;

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
