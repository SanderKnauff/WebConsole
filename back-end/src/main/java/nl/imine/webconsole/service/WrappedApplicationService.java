package nl.imine.webconsole.service;

import nl.imine.webconsole.dto.NewApplicationDto;
import nl.imine.webconsole.model.WrappedApplication;
import nl.imine.webconsole.repository.WrappedApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    /**
     * Creates a new application from the provided data
     *
     * @param newApplication the details of the new application
     * @return the UUID assigned to this application
     */
    public String createApplication(NewApplicationDto newApplication) {
        WrappedApplication wrappedApplication = new WrappedApplication(null, newApplication.getCommandString(), newApplication.getDebugString(), newApplication.getWorkingDirectory());
        WrappedApplication savedApplication = wrappedApplicationRepository.save(wrappedApplication);
        return savedApplication.getId();
    }

    public void deleteApplication(String applicationId) {
        wrappedApplicationRepository.deleteById(applicationId);
    }
}
