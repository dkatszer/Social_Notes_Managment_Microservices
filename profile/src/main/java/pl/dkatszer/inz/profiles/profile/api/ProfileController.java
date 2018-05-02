package pl.dkatszer.inz.profiles.profile.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dkatszer.inz.profiles.profile.entitites.Profile;
import pl.dkatszer.inz.profiles.profile.model.ProfileInfo;
import pl.dkatszer.inz.profiles.profile.model.mappers.ProfileMapper;
import pl.dkatszer.inz.profiles.profile.repositories.ProfileRepository;
import pl.dkatszer.inz.profiles.security.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-28.
 */
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileMapper profileMapper;


    @GetMapping("/{username}/friends")
    public List<ProfileInfo> getFriends(@PathVariable String username){
        final Profile profile = findByUsername(username);
        return profile.getMineFriends().stream().map(profileMapper::profileToProfileInfo).collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public ProfileInfo getProfileInfo(@PathVariable String username){
        return profileMapper.profileToProfileInfo(findByUsername(username));
    }

    private Profile findByUsername(@PathVariable String username) {
        return profileRepository.findByUser(userRepository.findByUsername(username));
    }
}
