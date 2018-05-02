package pl.dkatszer.inz.apigateway.security.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import pl.dkatszer.inz.apigateway.security.model.JwtAuthenticationRequest;
import pl.dkatszer.inz.apigateway.security.model.JwtAuthenticationResponse;
import pl.dkatszer.inz.apigateway.security.services.JwtAuthService;

/**
 * Created by doka on 2017-12-16.
 * TODO - move logic to service
 *
 */
@RestController
@RequestMapping("/token")
public class AuthenticationController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/auth")
    public ResponseEntity<JwtAuthenticationResponse> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
        performSecurity(authenticationRequest);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtAuthService.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    private void performSecurity(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
//
//    @GetMapping("/refresh")
//    public ResponseEntity<JwtAuthenticationResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        String token = request.getHeader(tokenHeader);
//        final Optional<String> username = jwtAuthService.getUsernameFromToken(token);
//        if(username.isPresent()) {
//            JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username.get());
//
//            if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//                String refreshedToken = jwtTokenUtil.refreshToken(token);
//                return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//            }
//        }
//        return ResponseEntity.badRequest().body(null);
//    }

}
