package pl.dkatszer.inz.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by doka on 2017-12-12.
 */
@SpringBootApplication
@EnableZuulProxy
//@EnableOAuth2Sso //automatically forward OAuth2 tokens to the services it is proxying.
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
