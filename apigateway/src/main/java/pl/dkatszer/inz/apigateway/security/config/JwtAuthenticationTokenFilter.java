package pl.dkatszer.inz.apigateway.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.dkatszer.inz.apigateway.security.services.JwtAuthService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by doka on 2017-12-17.
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthService jwtAuthService;

    @Value("${jwt.header}")
    private String tokenHeader;

    private RestTemplate restTemplate = new RestTemplate();

    private static final String TOKEN_HEADER_BEGINNING = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);

        if(authToken != null && authToken.startsWith(TOKEN_HEADER_BEGINNING)) {
            authToken = authToken.substring(TOKEN_HEADER_BEGINNING.length());
        }

        Optional<String> username = jwtAuthService.getUsernameFromToken(authToken);

        if (username.isPresent()&& SecurityContextHolder.getContext().getAuthentication() == null) {
            LOGGER.info("checking authentication for user " + username.get());

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username.get());

            if (jwtAuthService.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                LOGGER.info("authenticated user " + username.get() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
