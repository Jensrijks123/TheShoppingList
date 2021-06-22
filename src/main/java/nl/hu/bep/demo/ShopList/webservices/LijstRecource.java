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

        ResultSet rsLijst = stmt.executeQuery("select lijstnaam_combi from gebruiker_en_lijst where userid_id = '"+ gebruikerID +"'");

        ArrayList<String> lijstjes = new ArrayList<>();

        String lijstNaam = null;

        while (rsLijst.next()) {
            lijstNaam= rsLijst.getString("lijstnaam_combi");
            lijstjes.add(lijstNaam);
        }


        return !lijstjes.isEmpty() ? Response.ok(lijstjes).build() : Response.status(Response.Status.NO_CONTENT).entity(new AbstractMap.SimpleEntry<>("error", "There were no lists")).build();

    }



    @POST
    @Path("createLijst")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLijst(@FormParam("lijstNaam") String lijstName) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        User user = User.getAccount();

        int gebruikerID = user.getUserid();

        ResultSet rsLijst1 = stmt.executeQuery("select lijstnaam_combi from gebruiker_en_lijst where userid_id = '"+ gebruikerID +"'");

        ArrayList<String> lijstjes = new ArrayList<>();

        String lijstNaam = null;

        while (rsLijst1.next()) {
            lijstNaam= rsLijst1.getString("lijstnaam_combi");
            lijstjes.add(lijstNaam);
        }

        if (!lijstName.equals("") && !lijstjes.contains(lijstName)) {

            System.out.println(gebruikerID);

            ResultSet rsUser = stmt.executeQuery("select userid, gebruikersnaam, email, wachtwoord, rol from gebruiker where userid = '" + gebruikerID + "'");

            int userID = 0;

            while (rsUser.next()) {
                userID = rsUser.getInt("userid");
            }


            try {
                stmt.executeQuery("INSERT INTO boodschappenlijstje (lijstnaam, eigenaarlijst) VALUES ('" + lijstName + "', '" + gebruikerID + "')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            ResultSet rsLijst = stmt.executeQuery("select lijstid from boodschappenlijstje where eigenaarLijst = '" + gebruikerID + "' and lijstnaam = '" + lijstName + "'");

            int lijstID = 0;

            while (rsLijst.next()) {
                lijstID = rsLijst.getInt("lijstid");
            }
            System.out.println(lijstID);
            Boodschappenlijstje lijst = new Boodschappenlijstje(lijstName, lijstID);

            try {
                stmt.executeQuery("INSERT INTO gebruiker_en_lijst (lijstid_id, userid_id, lijstnaam_combi) VALUES ('" + lijstID + "', '" + userID + "', '"+ lijstName +"')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return Response.ok(lijst.getLijsteNaam()).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }

    @POST
    @Path("createLijstBack/{lijstNaam}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createLijstBack(@PathParam("lijstNaam") String lijstName) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        User user1 = User.getAccount();

        int userID1 = user1.getUserid();

        ResultSet rsLijst = stmt.executeQuery("select lijstid_id from gebruiker_en_lijst where userid_id = '" + userID1 + "' and lijstnaam_combi = '"+ lijstName +"'");

        int lijstID1 = 0;

        while (rsLijst.next()) {
            lijstID1 = rsLijst.getInt("lijstid_id");
        }

        Boodschappenlijstje lijstje =  new Boodschappenlijstje(lijstName, lijstID1);

        Boodschappenlijstje.setLijst(lijstje);

        return Response.ok().build();
    }
}
