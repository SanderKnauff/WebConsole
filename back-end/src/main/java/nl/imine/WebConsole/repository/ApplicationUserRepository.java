package nl.imine.WebConsole.repository;

import nl.imine.WebConsole.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {

    Optional<ApplicationUser> findOneByUsername(String name);

}
