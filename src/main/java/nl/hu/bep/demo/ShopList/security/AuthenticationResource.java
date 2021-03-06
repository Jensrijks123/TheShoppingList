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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;

@Path("/authentication")
public class AuthenticationResource {
    final static public Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username,
                                     @FormParam("password") String password)  throws ClassNotFoundException, SQLException {
        try {

            Class.forName("org.postgresql.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
            java.sql.Statement stmt = conn.createStatement();

            ResultSet rsUser = stmt.executeQuery("select gebruikersnaam, email, wachtwoord, rol, userid from gebruiker");

            int oke = 0;
            String gebruikersnaam = null;
            String role = null;
            String wachtwoord = null;
            String email = null;
            int userID = 0;

            while (rsUser.next()) {

                if (rsUser.getString(2).equals(username) && rsUser.getString(3).equals(password)) {
                    role = rsUser.getString("rol");
                    wachtwoord = rsUser.getString("wachtwoord");
                    email = rsUser.getString("email");
                    gebruikersnaam = rsUser.getString("gebruikersnaam");
                    userID = rsUser.getInt("userid");

                    oke = 10;
                }
            }

            User user =  new User(gebruikersnaam, email, wachtwoord,role,userID);

            User.setAccount(user);

            if (oke != 10) throw new IllegalArgumentException("No user found or invalid credentials");
            String token = createToken(username, role);

            SimpleEntry<String, String> JWT = new SimpleEntry<>("JWT", token);

            return Response.ok(JWT).build();

        } catch (JwtException | IllegalArgumentException e) {

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim(username, role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}

