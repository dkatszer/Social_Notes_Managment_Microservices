package pl.dkatszer.inz.apigateway.security.model;

import java.io.Serializable;

/**
 * Created by doka on 2017-12-16.
 */
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -3052417916780741152L;

    private String username;
    private String password;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
