package pl.dkatszer.inz.profiles.security.entities;

import pl.dkatszer.inz.profiles.profile.entitites.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by doka on 2017-12-16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    //have to be eager because we are not using session so we dont have anything which can work as PROXY.
    //we dont need to be it lazy because user can have only small roles
    // if we want to make it Lazy than we have to create proxy in JwtUser
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<UserRole> userRole;

    @Column(name = "last_password_reset_date")
    private LocalDateTime lastPasswordResetDate;

    @OneToOne(mappedBy = "user")
    private Profile profile;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        lastPasswordResetDate = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }

    public LocalDateTime getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(LocalDateTime lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
}
