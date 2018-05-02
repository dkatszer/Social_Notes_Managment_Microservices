package pl.dkatszer.inz.apigateway.security.model;

import java.io.Serializable;

/**
 * Created by doka on 2017-12-16.
 */
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 7386102084455753431L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
