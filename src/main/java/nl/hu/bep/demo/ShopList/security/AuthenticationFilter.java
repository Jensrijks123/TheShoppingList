package nl.hu.bep.demo.ShopList.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import nl.hu.bep.demo.ShopList.model.User;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestCtx) {

        boolean isSecure = requestCtx.getSecurityContext().isSecure();
        String scheme = requestCtx.getUriInfo().getRequestUri().getScheme();
        MySecurityContext msc = new MySecurityContext(null, scheme);
        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring("Bearer".length()).trim();

            try {

                JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);

                Claims claims = parser.parseClaimsJws(token).getBody();

                String user = claims.getSubject();

                msc = new MySecurityContext(User.getUserByUserName(user), scheme);


            } catch (JwtException | IllegalArgumentException e) {

            }
        }

        requestCtx.setSecurityContext(msc);
    }
}