package nl.hu.bep.demo.ShopList.webservices;

import nl.hu.bep.demo.ShopList.model.Country;
import nl.hu.bep.demo.ShopList.model.User;
import nl.hu.bep.demo.ShopList.model.World;
import nl.hu.bep.demo.ShopList.model.MyUser;

import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.StringReader;
import java.util.AbstractMap;
import java.util.List;

@Path("shoppinglist")
public class ShoppingListResource {


    @GET
    @Path("profile")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProfile(@Context SecurityContext sc) {
        if (sc.getUserPrincipal() instanceof User) {
            User current = (User) sc.getUserPrincipal();
            return Json.createObjectBuilder()
                    .add("username", current.getName())
                    .add("password", "nope")
                    .build()
                    .toString();
        }
        return Json.createObjectBuilder()
                .add("error", "Something went wrong, please contact your administrator")
                .build()
                .toString();
    }

}

