package pl.dkatszer.inz.apigateway.security.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-28.
 */
public class JwtUserRequest {
    public String username;
    public String password;
    public Set<AuthRole> authorities;
    public boolean enabled;
    public LocalDateTime lastPasswordResetDate;

    public static JwtUserRequest of(JwtUser user) {
        JwtUserRequest result = new JwtUserRequest();
        result.username = user.getUsername();
        result.password = user.getPassword();
        result.authorities = user.getAuthorities().stream().map(auth -> AuthRole.valueOf(auth.getAuthority())).collect(Collectors.toSet());
        result.enabled = user.isEnabled();
        result.lastPasswordResetDate = user.getLastPasswordResetDate();
        return result;
    }
}
