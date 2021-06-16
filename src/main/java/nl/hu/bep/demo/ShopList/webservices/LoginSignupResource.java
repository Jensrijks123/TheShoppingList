package nl.hu.bep.demo.ShopList.webservices;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Response;

import com.azure.core.annotation.Post;
import nl.hu.bep.demo.ShopList.model.User;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap;

@Path("LoginSignup")
public class LoginSignupResource {


    @GET
    @Path("profile")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProfile(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User) {
            User current = (User) sc.getUserPrincipal();
            return Json.createObjectBuilder()
                    .add("username", current.getName())
                    .add("password", current.getPassword())
                    .build()
                    .toString();
        }
        return Json.createObjectBuilder()
                .add("error", "Something went wrong, please contact your administrator")
                .build()
                .toString();
    }


    @POST
    @Path("signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@FormParam("usernameSign") String usernameF,
                               @FormParam("emailSign") String emailF,
                               @FormParam("passwordSign1") String password1F,
                               @FormParam("passwordSign2") String password2F) throws ClassNotFoundException, SQLException {


        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Response.ResponseBuilder response = Response.status(Response.Status.CONFLICT);


        ResultSet rsUser = stmt.executeQuery("select userid, gebruikersnaam, email, wachtwoord, rol from gebruiker where email = '"+ emailF +"'");

        if (password1F.equals(password2F)) {

            if (rsUser.next()) {
                response = Response.status(Response.Status.CONFLICT);
            } else {

                try {
                    stmt.executeQuery("INSERT INTO gebruiker (gebruikersnaam, email, wachtwoord, rol) VALUES ('" + usernameF + "', '" + emailF + "', '" + password1F + "', 'user')");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    response = Response.ok();
                    int gebruikerID = 0;

                    try {

                        ResultSet rsUserID = stmt.executeQuery("select gebruikersnaam, email, wachtwoord, userid from gebruiker");



                        while (rsUserID.next()) {

                            if (rsUserID.getString(1).equals(usernameF) && rsUserID.getString(3).equals(password1F) && rsUserID.getString(2).equals(emailF)) {
                                gebruikerID = rsUserID.getInt(4);
                          }
                        }

                    } catch (Exception b) {
                        System.out.println(b.getMessage());
                    }

                    System.out.println(gebruikerID);

                    String rolgebruiker = "user";

                    User user = new User(usernameF, emailF, password1F, rolgebruiker, gebruikerID);

                    User.setAccount(user);

                }
            }
        } else {
            response = Response.status(406);
        }

        return response.build();
    }
}

