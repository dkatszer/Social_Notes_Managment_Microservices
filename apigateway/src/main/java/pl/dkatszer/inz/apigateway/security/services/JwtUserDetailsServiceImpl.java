package pl.dkatszer.inz.apigateway.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.dkatszer.inz.apigateway.security.model.JwtUser;
import pl.dkatszer.inz.apigateway.security.model.JwtUserRequest;

import java.util.Optional;

/**
 * Created by doka on 2017-12-16.
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private JwtAuthService jwtAuthService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JwtUserRequest> jwtUser = jwtAuthService.getJwtUserRequest(username);

        if (!jwtUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUser.of(jwtUser.get());
        }
    }
}
