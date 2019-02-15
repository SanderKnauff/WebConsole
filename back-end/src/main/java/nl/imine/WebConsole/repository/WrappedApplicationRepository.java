package nl.imine.WebConsole.repository;

import nl.imine.WebConsole.model.WrappedApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WrappedApplicationRepository extends JpaRepository<WrappedApplication, String> {

}
