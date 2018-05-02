package pl.dkatszer.inz.apigateway.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.dkatszer.inz.apigateway.security.model.JsonDTO;
import pl.dkatszer.inz.apigateway.security.model.JwtUser;
import pl.dkatszer.inz.apigateway.security.model.JwtUserRequest;
import pl.dkatszer.inz.apigateway.security.model.JwtValidationRequest;

import java.util.Optional;

/**
 * Created by doka on 2017-12-27.
 */

@Service
public class JwtAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthService.class);
    public String baseUrl;
    private RestTemplate rest = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    public JwtAuthService(@Value("${microservices.profiles.url}") String profileUrl) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        baseUrl = profileUrl + "/api/auth/token";
    }

    public Optional<String> getUsernameFromToken(String authToken) {
        LOGGER.info("getUsernameFromToken - {}", authToken);
        final String url = baseUrl + "/username";
        LOGGER.info("sending request - url: {}  , method: {}",url,"POST");
        ResponseEntity<JsonDTO> response = rest.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(JsonDTO.of(authToken), headers),
                JsonDTO.class);
        return Optional.ofNullable((String)response.getBody().value);
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        LOGGER.info("validateToken - username: {}, password: {}, token: {}",userDetails.getUsername(), userDetails.getPassword(), authToken);
        JwtUser jwtUser = (JwtUser) userDetails;
        final String url = baseUrl + "/validation";
        LOGGER.info("sending request - url: {}  , method: {}",url,"POST");
        ResponseEntity<JsonDTO> response = rest.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(JwtValidationRequest.of(JwtUserRequest.of(jwtUser), authToken), headers),
                JsonDTO.class);
        return response.getBody() != null && ((boolean) response.getBody().value);
    }

    public Optional<JwtUserRequest> getJwtUserRequest(String username) {
        LOGGER.info("getJwtUserRequest - {}", username);
        final String url = baseUrl + "/user";
        LOGGER.info("sending request - url: {}  , method: {}",url,"POST");
        final ResponseEntity<JwtUserRequest> response = rest.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(JsonDTO.of(username), headers),
                JwtUserRequest.class
        );
        return Optional.ofNullable(response.getBody());
    }

    public String generateToken(String username) {
        LOGGER.info("generateToken - {}", username);
        final String url = baseUrl ;
        LOGGER.info("sending request - url: {}  , method: {}",url,"POST");
        ResponseEntity<JsonDTO> response = rest.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(JsonDTO.of(username), headers),
                JsonDTO.class);
        return (String) response.getBody().value;
    }
}
