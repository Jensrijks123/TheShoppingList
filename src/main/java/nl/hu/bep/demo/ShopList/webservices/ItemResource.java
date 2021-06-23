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

@Path("item")
public class ItemResource {


    @GET
    @Path("loadItemsReady")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemReady() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();
        int boodschappenlijstjeId = boodschappenlijstje.getLijstid();

        String status = "ready";

        ResultSet rsItem = stmt.executeQuery("select itemnaam_combi from item_en_lijst where lijstid_id = '"+ boodschappenlijstjeId +"' and status_item = '"+ status +"'");

        ArrayList<String> items = new ArrayList<>();

        String itemNaam = null;

        while (rsItem.next()) {
            itemNaam= rsItem.getString("itemnaam_combi");
            items.add(itemNaam);
        }

        return !items.isEmpty() ? Response.ok(items).build() : Response.status(Response.Status.NO_CONTENT).entity(new AbstractMap.SimpleEntry<>("error", "There were no items")).build();

    }

    @GET
    @Path("loadItemsDone")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemDone() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();
        int boodschappenlijstjeId = boodschappenlijstje.getLijstid();

        String status = "done";

        ResultSet rsItem = stmt.executeQuery("select itemnaam_combi from item_en_lijst where lijstid_id = '"+ boodschappenlijstjeId +"' and status_item = '"+ status +"'");

        ArrayList<String> items = new ArrayList<>();

        String itemNaam = null;

        while (rsItem.next()) {
            itemNaam= rsItem.getString("itemnaam_combi");
            items.add(itemNaam);
        }

        return !items.isEmpty() ? Response.ok(items).build() : Response.status(Response.Status.NO_CONTENT).entity(new AbstractMap.SimpleEntry<>("error", "There were no items")).build();
    }



    @POST
    @Path("createItemReady")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItemReady(@FormParam("itemNaam") String itemNaam) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

        int lijstID = boodschappenlijstje.getLijstid();

        ResultSet rsItemNaam = stmt.executeQuery("select itemnaam_combi from item_en_lijst where lijstid_id = '"+ lijstID +"'");

        String itemNaam123 = null;

        ArrayList<String> items = new ArrayList<>();

        while (rsItemNaam.next()) {
            itemNaam123 = rsItemNaam.getString("itemnaam_combi");
            items.add(itemNaam123);
        }

        if (!itemNaam.equals("") && !items.contains(itemNaam)) {

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
            String status = "ready";

            try {
                stmt.executeQuery("INSERT INTO item_en_lijst (itemid_id, lijstid_id, itemnaam_combi, status_item) VALUES ('" + itemID + "', '" + lijstID + "', '"+ itemNaam +"', '"+ status +"')");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return Response.ok(item.getItemNaam()).build();

        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }
    }

    @POST
    @Path("createItemDone/{itemNaam}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItemDone(@PathParam("itemNaam") String itemNaam) throws ClassNotFoundException, SQLException {

        if (!itemNaam.equals("")) {
            Class.forName("org.postgresql.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
            java.sql.Statement stmt = conn.createStatement();

            Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

            int lijstID = boodschappenlijstje.getLijstid();

            String status = "done";

            stmt.executeUpdate("update item_en_lijst set status_item = '"+ status +"' where itemnaam_combi = '"+ itemNaam +"' and lijstid_id = '"+ lijstID +"'");

            return Response.ok(itemNaam).build();

        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }
    }

    @POST
    @Path("createItemBackToReady/{itemNaam}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createItemBackToReady(@PathParam("itemNaam") String itemNaam) throws ClassNotFoundException, SQLException {

        if (!itemNaam.equals("")) {
            Class.forName("org.postgresql.Driver");
            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
            java.sql.Statement stmt = conn.createStatement();

            Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

            int lijstID = boodschappenlijstje.getLijstid();

            String status = "ready";

            stmt.executeUpdate("update item_en_lijst set status_item = '"+ status +"' where itemnaam_combi = '"+ itemNaam +"' and lijstid_id = '"+ lijstID +"'");

            return Response.ok(itemNaam).build();

        } else {
            return Response.status(Response.Status.NO_CONTENT).build();

        }
    }

    @POST
    @Path("deleteSelectedItems")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSelectedItems() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
        java.sql.Statement stmt = conn.createStatement();

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();

        int lijstID = boodschappenlijstje.getLijstid();

        String status = "done";

        ResultSet rsItemIDs = stmt.executeQuery("select itemid_id from item_en_lijst where lijstid_id = '" + lijstID + "' and  status_item = '" + status + "'");

        ArrayList<String> itemIDs = new ArrayList<>();

        int itemid = 0;

        while (rsItemIDs.next()) {
            itemid = rsItemIDs.getInt("itemid_id");
            itemIDs.add("" + itemid);
        }

        for (String item1 : itemIDs) {
            try {
                stmt.executeUpdate("delete from item_en_lijst where itemid_id = '"+ item1 +"'");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                stmt.executeUpdate("delete from item where itemid = '"+ item1 +"'");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return Response.ok().build();
    }

    @GET
    @Path("loadLijstNaam")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLijstNaam() {

        Boodschappenlijstje boodschappenlijstje = Boodschappenlijstje.getLijst();
        String boodschappenlijstjeNaam = boodschappenlijstje.getLijsteNaam();

        ArrayList<String> lijstNamen = new ArrayList<>();

        lijstNamen.add(boodschappenlijstjeNaam);

        System.out.println(lijstNamen);

        return !lijstNamen.isEmpty() ? Response.ok(lijstNamen).build() : Response.status(Response.Status.NO_CONTENT).entity(new AbstractMap.SimpleEntry<>("error", "There was no list name")).build();

    }


}
