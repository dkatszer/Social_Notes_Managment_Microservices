package pl.dkatszer.inz.profiles.security.model;

import pl.dkatszer.inz.profiles.security.entities.AuthRole;
import pl.dkatszer.inz.profiles.security.entities.User;
import pl.dkatszer.inz.profiles.security.entities.UserRole;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-16.
 */
public class JwtUser {
    public String username;
    public String password;
    public Set<AuthRole> authorities;
    public boolean enabled;
    public LocalDateTime lastPasswordResetDate;

    public static JwtUser of(User user) {
        final JwtUser jwtUser = new JwtUser();
        jwtUser.username = user.getUsername();
        jwtUser.password = user.getPassword();
        jwtUser.authorities = user.getUserRole().stream().map(UserRole::getRole).collect(Collectors.toSet());
        jwtUser.enabled = user.isEnabled();
        jwtUser.lastPasswordResetDate = user.getLastPasswordResetDate();
        return jwtUser;
    }

}
