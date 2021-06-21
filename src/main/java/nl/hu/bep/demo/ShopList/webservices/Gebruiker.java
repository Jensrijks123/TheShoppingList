package nl.hu.bep.demo.ShopList.webservices;

import nl.hu.bep.demo.ShopList.model.Boodschappenlijstje;
import nl.hu.bep.demo.ShopList.model.Item;
import nl.hu.bep.demo.ShopList.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

@Path("user")
public class Gebruiker {

    @GET
    @Path("loadUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadUsers() throws SQLException, ClassNotFoundException {

        System.out.println("begin");

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        User user = User.getAccount();
        String gebruikerID = user.getEmail();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();
        int boodschappenlijstjeId = boodschappenlijstje.getLijstid();

        ResultSet rsUserIDs = stmt.executeQuery("select userid_id, lijstid_id from gebruiker_en_lijst where lijstid_id = '"+ boodschappenlijstjeId +"'");

        ArrayList<String> gebruikersId = new ArrayList<>();
        ArrayList<String> gebruikersEmail = new ArrayList<>();

        int userID = 0;
        int lijstID = 0;
        String userEmail = null;

        while (rsUserIDs.next()) {
            userID= rsUserIDs.getInt("userid_id");
            lijstID = rsUserIDs.getInt("lijstid_id");
            gebruikersId.add("" +userID);
        }

        for (String user1 : gebruikersId) {
            ResultSet rsUserEmail = stmt.executeQuery("select email from gebruiker where userid = '"+ user1 +"'");

            if (rsUserEmail.next()) {
                userEmail = rsUserEmail.getString("email");
                System.out.println(userEmail);
                gebruikersEmail.add(userEmail);
            }
        }
        System.out.println(gebruikersEmail);
        gebruikersEmail.remove(gebruikerID);
        System.out.println(gebruikersEmail);

        return !gebruikersEmail.isEmpty() ? Response.ok(gebruikersEmail).build() : Response.status(Response.Status.NO_CONTENT).entity(new AbstractMap.SimpleEntry<>("error", "There were no users")).build();



    }

    @POST
    @Path("voegUserToe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response voegUserToe(@FormParam("addUserInput") String userInput) throws ClassNotFoundException, SQLException {

        System.out.println(userInput);

        if (!userInput.equals("")) {
            Class.forName("org.postgresql.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
            java.sql.Statement stmt = conn.createStatement();

            Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

            int lijstID = boodschappenlijstje.getLijstid();
            String lijstNaam = boodschappenlijstje.getLijsteNaam();

            System.out.println(lijstID);
            System.out.println(lijstNaam);

            ResultSet rsUser = stmt.executeQuery("select userid from gebruiker where email = '" + userInput + "'");

            int userID = 0;

            while (rsUser.next()) {
                userID = rsUser.getInt("userid");
            }

            System.out.println(userID);

            try {
                stmt.executeQuery("INSERT INTO gebruiker_en_lijst (lijstid_id, userid_id, lijstnaam_combi) VALUES ('" + lijstID + "', '"+ userID +"', '"+ lijstNaam +"')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


            return Response.ok(userInput).build();

        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }

    }
}
