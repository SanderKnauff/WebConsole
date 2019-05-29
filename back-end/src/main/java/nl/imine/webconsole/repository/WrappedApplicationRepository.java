package nl.imine.webconsole.repository;

import nl.imine.webconsole.model.WrappedApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrappedApplicationRepository extends JpaRepository<WrappedApplication, String> {

}
