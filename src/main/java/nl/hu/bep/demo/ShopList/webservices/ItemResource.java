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
import java.util.ArrayList;

@Path("item")
public class ItemResource {


    @GET
    @Path("loadItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();
        int boodschappenlijstjeId = boodschappenlijstje.getLijstid();

        ResultSet rsItem = stmt.executeQuery("select itemnaam_combi from item_en_lijst where lijstid_id = '"+ boodschappenlijstjeId +"'");

        ArrayList<String> items = new ArrayList<>();

        String itemNaam = null;

        while (rsItem.next()) {
            itemNaam= rsItem.getString("itemnaam_combi");
            items.add(itemNaam);
        }

        return Response.ok(items).build();
    }



    @POST
    @Path("createItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItem(@FormParam("itemNaam") String itemNaam) throws ClassNotFoundException, SQLException {

        if (!itemNaam.equals("")) {
            Class.forName("org.postgresql.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
            java.sql.Statement stmt = conn.createStatement();

            Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

            int lijstID = boodschappenlijstje.getLijstid();

            try {
                stmt.executeQuery("INSERT INTO item (itemnaam) VALUES ('" + itemNaam + "')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            ResultSet rsItem = stmt.executeQuery("select itemid from item where itemnaam = '" + itemNaam + "'");

            int itemID = 0;

            while (rsItem.next()) {
                itemID = rsItem.getInt("itemid");
            }

            Item item = new Item(itemNaam, itemID);

            try {
                stmt.executeQuery("INSERT INTO item_en_lijst (itemid_id, lijstid_id, itemnaam_combi) VALUES ('" + itemID + "', '" + lijstID + "', '"+ itemNaam +"')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return Response.ok(item.getItemNaam()).build();

        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }

    }
}
