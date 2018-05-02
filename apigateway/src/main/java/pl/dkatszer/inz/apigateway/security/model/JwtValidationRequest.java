package pl.dkatszer.inz.apigateway.security.model;

import java.io.Serializable;

/**
 * Created by doka on 2017-12-28.
 */
public class JwtValidationRequest implements Serializable{

    private static final long serialVersionUID = 6965827977665913003L;

    public JwtUserRequest jwtUser;
    public String token;

    public static JwtValidationRequest of(JwtUserRequest jwtUser, String token){
        final JwtValidationRequest req = new JwtValidationRequest();
        req.jwtUser = jwtUser;
        req.token = token;
        return req;
    }
}
