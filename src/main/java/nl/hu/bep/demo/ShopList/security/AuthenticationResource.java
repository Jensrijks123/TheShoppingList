package nl.hu.bep.demo.ShopList.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.bep.demo.ShopList.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;

@Path("/authentication")
public class AuthenticationResource {
    final static public Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password) {
        try {
            String role = User.validateLogin(username, password);


            if (role == null) throw new IllegalArgumentException("No user found or invalid credentials");
            String token = createToken(username, role);

            SimpleEntry<String, String> JWTText = new SimpleEntry<>("JWT", token);

            System.out.println("wel geautoriseerd"); //asdasdadsadsadasdasd

            return Response.ok(JWTText).build();
        } catch (JwtException | IllegalArgumentException e) {

            System.out.println("Niet geautorizeerd"); //asdasdadsadsadasdasd
            return Response.status(Response.Status.UNAUTHORIZED).build();

        }
    }

    private String createToken(String username, String role) {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}

