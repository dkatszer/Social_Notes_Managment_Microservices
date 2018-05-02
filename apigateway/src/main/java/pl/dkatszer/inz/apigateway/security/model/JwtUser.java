package pl.dkatszer.inz.apigateway.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by doka on 2017-12-16.
 */
public class JwtUser implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final LocalDateTime lastPasswordResetDate;

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities, boolean enabled, LocalDateTime lastPasswordResetDate) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public LocalDateTime getLastPasswordResetDate(){
        return lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static JwtUser of(JwtUserRequest user) {
        return new JwtUser(
                user.username,
                user.password,
                mapToGrantedAuthority(user.authorities),
                user.enabled,
                user.lastPasswordResetDate);
    }

    private static List<GrantedAuthority> mapToGrantedAuthority(Set<AuthRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
                .collect(Collectors.toList());
    }
}
