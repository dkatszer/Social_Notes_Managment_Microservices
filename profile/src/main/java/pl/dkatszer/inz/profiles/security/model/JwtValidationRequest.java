package pl.dkatszer.inz.profiles.security.model;

import java.io.Serializable;

/**
 * Created by doka on 2017-12-28.
 */
public class JwtValidationRequest implements Serializable {

    private static final long serialVersionUID = -5865655210501336040L;

    public JwtUser jwtUser;
    public String token;

    public static JwtValidationRequest of(JwtUser jwtUser, String token) {
        final JwtValidationRequest req = new JwtValidationRequest();
        req.jwtUser = jwtUser;
        req.token = token;
        return req;
    }
}
