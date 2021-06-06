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

@Path("LoginSignup")
public class ShoppingListResource {

    @GET
    @Path("profile")
//    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProfile(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User) {
            User current = (User) sc.getUserPrincipal();
            System.out.println("Goed");
//            respone.setContentType("home/html");
            return Json.createObjectBuilder()
                    .add("username", current.getName())
                    .add("password", current.getPassword())
                    .build()
                    .toString();
        }
        System.out.println("Slecht");
        return Json.createObjectBuilder()
                .add("error", "Something went wrong, please contact your administrator")
                .build()
                .toString();
    }



    @POST
    @Path("signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@FormParam("usernameSign") String username,
                               @FormParam("emailSign") String email,
                               @FormParam("passwordSign1") String password1,
                               @FormParam("passwordSign2") String password2) throws ClassNotFoundException, SQLException {


        System.out.println("aadad");

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        ResultSet rsUser = stmt.executeQuery("select * from gebruiker");
//        ResultSet rsUser = stmt.executeQuery("insert into gebruiker(username, email, wachtwoord, rol) values('stijn', 'stijn@stijn.com', 'stijn', 'user')");
        String naam = "";

        while (rsUser.next()) {
            naam = rsUser.getString(2);
            System.out.println(naam);
        }
        if (username.equals("Jens")) {
            return Response.ok().build();
        } else {
            return Response.noContent().status(401).build();
        }



    }

//    @POST
//    @Path("signup")
//    @Produces(MediaType.APPLICATION_JSON)
//    public User creatUser() {
//        return User.getUserByUserName("admin");
//    }

}

