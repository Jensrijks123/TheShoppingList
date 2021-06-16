package nl.hu.bep.demo.ShopList.webservices;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Response;
import com.azure.core.annotation.Post;
import nl.hu.bep.demo.ShopList.model.Boodschappenlijstje;
import nl.hu.bep.demo.ShopList.model.User;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.awt.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

@Path("lijst")
public class LijstRecource {
    public User user;
    public Boodschappenlijstje boodschappenlijstje;

    @GET
    @Path("loadLijsten")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLijst() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        User user = User.getAccount();
        int gebruikerID = user.getUserid();
        ResultSet rsLijst = stmt.executeQuery("select lijstid, lijstnaam from boodschappenlijstje where eigenaarLijst = '"+ gebruikerID +"'");

        ArrayList<String> lijstjes = new ArrayList<>();

        int lijstID = 0;
        String lijstNaam = null;

        while (rsLijst.next()) {
            lijstID = rsLijst.getInt("lijstid");
            lijstNaam= rsLijst.getString("lijstnaam");
            lijstjes.add(lijstNaam);
        }

        System.out.println(lijstjes);

//        ArrayList<HashMap<String,String>> info = new ArrayList<>();

        return Response.ok(lijstjes).build();
    }



    @POST
    @Path("createLijst")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLijst(@FormParam("lijstNaam") String lijstName) throws ClassNotFoundException, SQLException {


        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();


//        Response.ResponseBuilder response = Response.ok();




        System.out.println(lijstName);

        User user = User.getAccount();



        int gebruikerID = user.getUserid();

        System.out.println(gebruikerID);

        ResultSet rsUser = stmt.executeQuery("select userid, gebruikersnaam, email, wachtwoord, rol from gebruiker where userid = '"+gebruikerID+"'");


        int oke = 0;
        String gebruikersnaam = null;
        String role = null;
        String wachtwoord = null;
        String email = null;
        int userID = 0;

        while (rsUser.next()) {
            role = rsUser.getString("rol");
            wachtwoord = rsUser.getString("wachtwoord");
            email = rsUser.getString("email");
            gebruikersnaam = rsUser.getString("gebruikersnaam");
            userID = rsUser.getInt("userid");
        }


        try {
            stmt.executeQuery("INSERT INTO boodschappenlijstje (lijstnaam, eigenaarlijst) VALUES ('" +lijstName + "', '"+ gebruikerID +"')");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            response = Response.ok(lijstName);
        }

        ResultSet rsLijst = stmt.executeQuery("select lijstid from boodschappenlijstje where eigenaarLijst = '"+ gebruikerID +"' and lijstnaam = '" +lijstName + "'");

        int lijstID = 0;

        while (rsLijst.next()) {
            lijstID = rsLijst.getInt("lijstid");
        }
        System.out.println(lijstID);
        Boodschappenlijstje lijst = new Boodschappenlijstje(lijstName, lijstID);

        try {
            stmt.executeQuery("INSERT INTO gebruiker_en_lijst (lijstid_id, userid_id) VALUES ('" +lijstID + "', '"+ userID +"')");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            response = Response.ok().entity(lijstName);
        }


        System.out.println(lijst.getLijsteNaam());


        return Response.ok(lijst.getLijsteNaam()).build();
    }
}
