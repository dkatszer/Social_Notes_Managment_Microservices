package pl.dkatszer.inz.profiles.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dkatszer.inz.profiles.security.entities.UserRole;

/**
 * Created by doka on 2017-12-16.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
