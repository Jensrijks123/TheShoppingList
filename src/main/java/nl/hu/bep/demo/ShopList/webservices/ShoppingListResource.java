package nl.hu.bep.demo.ShopList.webservices;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Response;
import nl.hu.bep.demo.ShopList.model.User;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("shoppinglist")
public class ShoppingListResource {


//    @GET
//    @Path("profile")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response verifyLogin() {
//
//    }



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

}

