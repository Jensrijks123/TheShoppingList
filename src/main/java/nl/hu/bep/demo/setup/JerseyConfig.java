package nl.hu.bep.demo.setup;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("restservices")
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("nl.hu.bep.demo.ShopList.webservices, nl.hu.bep.demo.ShopList.security");
        register(RolesAllowedDynamicFeature.class);
    }
}
