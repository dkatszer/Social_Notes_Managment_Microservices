package pl.dkatszer.inz.profiles.security.entities;

import javax.persistence.*;

/**
 * Created by doka on 2017-12-16.
 */
@Entity
@Table(name = "authorities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username", "authority"})
        })
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id",
            unique = true, nullable = false)
    private Integer userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false, length = 50)
    private AuthRole role;

    public UserRole() {
    }

    public UserRole(User user, AuthRole role) {
        this.user = user;
        this.role = role;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }
}