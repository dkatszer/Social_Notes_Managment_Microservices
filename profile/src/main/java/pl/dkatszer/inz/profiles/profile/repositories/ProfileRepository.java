package pl.dkatszer.inz.profiles.profile.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dkatszer.inz.profiles.profile.entitites.Profile;
import pl.dkatszer.inz.profiles.security.entities.User;

/**
 * Created by doka on 2017-12-28.
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByUser(User user);
}
