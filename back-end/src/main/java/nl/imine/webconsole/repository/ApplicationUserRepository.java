package nl.imine.webconsole.repository;

import nl.imine.webconsole.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {

    Optional<ApplicationUser> findOneByUsername(String name);

}
