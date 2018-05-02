package pl.dkatszer.inz.profiles.profile.entitites;

import pl.dkatszer.inz.profiles.security.entities.User;

import javax.persistence.*;
import java.util.List;

/**
 * Created by doka on 2017-12-28.
 */
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = false)
    private User user;

    @JoinTable(name = "friendship", joinColumns = {
            @JoinColumn(name = "profile", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "friend", referencedColumnName = "id", nullable = false)})
    @ManyToMany
    private List<Profile> mineFriends;

    @ManyToMany(mappedBy = "mineFriends")
    private List<Profile> profilesForWhichIAmFriend;

    //some additional data

    private String avatarSrc;

    public Profile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Profile> getMineFriends() {
        return mineFriends;
    }

    public void setMineFriends(List<Profile> mineFriends) {
        this.mineFriends = mineFriends;
    }

    public List<Profile> getProfilesForWhichIAmFriend() {
        return profilesForWhichIAmFriend;
    }

    public void setProfilesForWhichIAmFriend(List<Profile> profilesForWhichIAmFriend) {
        this.profilesForWhichIAmFriend = profilesForWhichIAmFriend;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }
}
