package pl.dkatszer.inz.notes.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.dkatszer.inz.notes.model.ProfileInfo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by doka on 2017-12-29.
 */
@Component
public class AuthorsService {
    public static RestTemplate rest = new RestTemplate();
    private static String profilMicroserviceUrl = "http://localhost:8092";

    public List<ProfileInfo> getFriends(String username) {
        String url = profilMicroserviceUrl + String.format("/api/profiles/%s/friends", username);
        ProfileInfo[] result = AuthorsService.rest.getForEntity(url, ProfileInfo[].class).getBody();
        return result.length == 0 ? null : Arrays.asList(result);
    }
}
