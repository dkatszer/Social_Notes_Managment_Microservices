package pl.dkatszer.inz.profiles.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.dkatszer.inz.profiles.security.model.JsonDTO;
import pl.dkatszer.inz.profiles.security.model.JwtUser;
import pl.dkatszer.inz.profiles.security.model.JwtValidationRequest;
import pl.dkatszer.inz.profiles.security.repositories.UserRepository;
import pl.dkatszer.inz.profiles.security.service.JwtService;

/**
 * Created by doka on 2017-12-28.
 */

@RestController
@RequestMapping("/api/auth/token")
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/username")
    public JsonDTO getUsernameFromToken(@RequestBody JsonDTO<String> token){
        return JsonDTO.of(jwtService.getUsernameFromToken(token.value).orElse(null));
    }

    @PostMapping("/validation")
    public JsonDTO getTokenValidationResult(@RequestBody JwtValidationRequest jwtValidationRequest){
        return JsonDTO.of(jwtService.validateToken(jwtValidationRequest.token,jwtValidationRequest.jwtUser));
    }

    @PostMapping("/user")
    public JwtUser getUser(@RequestBody JsonDTO<String> username){
        return JwtUser.of(userRepository.findByUsername( username.value));
    }

    @PostMapping
    public JsonDTO getToken(@RequestBody JsonDTO<String> username){
        return JsonDTO.of(jwtService.generateToken(username.value));
    }
}
