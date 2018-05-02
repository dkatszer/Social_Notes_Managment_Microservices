package pl.dkatszer.inz.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.dkatszer.inz.profiles.profile.entitites.Profile;
import pl.dkatszer.inz.profiles.profile.repositories.ProfileRepository;
import pl.dkatszer.inz.profiles.security.entities.AuthRole;
import pl.dkatszer.inz.profiles.security.entities.User;
import pl.dkatszer.inz.profiles.security.entities.UserRole;
import pl.dkatszer.inz.profiles.security.repositories.UserRepository;
import pl.dkatszer.inz.profiles.security.repositories.UserRoleRepository;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-16.
 */
@Component
@org.springframework.context.annotation.Profile("dev")
public class DevInitializer implements ApplicationListener<ApplicationReadyEvent> {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        saveUserWithProfile("admin", "$2a$10$dacvQyI/sKny.flSp1cpoOvTsG4vnH/pSemL9jcxNVVdNATfd2Waq", AuthRole.ROLE_ADMIN );
        saveUserWithProfile("user", "$2a$10$9ZwfXLYKEm2XfrqWVYNdtuSLohD/KJF6fWuBoUXFSeFEMRYvTkI5y", AuthRole.ROLE_USER, "https://kids.nationalgeographic.com/content/dam/kids/photos/animals/Fish/H-P/pufferfish-closeup.ngsversion.1427141760081.adapt.1900.1.jpg");
        saveUserWithProfile("student", "$2a$10$wopmSTw3sUGnXYcP0hAL5edWnPqXJUlt.QY5nPuqQkYA4TMdWPZqG", AuthRole.ROLE_USER, "https://www.petmd.com/sites/default/files/petmd-cat-happy-10.jpg");
        saveUserWithProfile("guest", "$2a$10$aIUL3/k0bUtUpM2l1ga5qePl1TNRKReBonceO3wNyLR9kGzrfTi/a", AuthRole.ROLE_USER, "http://www.lavanguardia.com/r/GODO/LV/p4/WebSite/2017/09/13/Recortada/img_ysaiz_20170913-165726_imagenes_lv_getty_istock-162136091-kcuG-U431258657972RiF-992x558@LaVanguardia-Web.jpg");

        final Map<String, List<String>> relations = Map.of(
                "admin", Collections.emptyList(),
                "user", List.of("student"),
                "student", List.of("user", "guest"),
                "guest", List.of("student")
        );

        setFriends(relations);
    }

    private void saveUserWithProfile(String username, String password, AuthRole role, String avatarSrc) {
        User user = new User(username, password);
        UserRole userRole = new UserRole(user, role);
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setAvatarSrc(avatarSrc);
        userRepository.save(user);
        userRoleRepository.save(userRole);
        profileRepository.save(profile);
    }
    private void saveUserWithProfile(String username, String password, AuthRole role) {
        saveUserWithProfile(username,password,role,"https://material.angular.io/assets/img/examples/shiba2.jpg");
    }

    @Transactional
    void setFriends(Map<String, List<String>> relations) {
        relations.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        findProfileByUsername(entry.getKey()),
                        entry.getValue().stream().map(this::findProfileByUsername).collect(Collectors.toList())
                ))
                .forEach(entry -> {
                    entry.getKey().setMineFriends(entry.getValue());
                    profileRepository.saveAndFlush(entry.getKey());
                });
    }

    private Profile findProfileByUsername(String username) {
        final User user = userRepository.findByUsername(username);
        return profileRepository.findByUser(user);
    }
}
