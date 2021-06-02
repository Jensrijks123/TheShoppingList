package nl.hu.bep.demo.setup;

import nl.hu.bep.demo.ShopList.model.Boodschappenlijstje;
import nl.hu.bep.demo.ShopList.model.Item;
import nl.hu.bep.demo.ShopList.model.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing application");
        User u = new User("Djensman", "jens.rijks@student.hu.nl", "Password", "admin");
        Item i = new Item("1,5 liter melk");
        Boodschappenlijstje b = new Boodschappenlijstje("Gezins Lijst");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Terminating application");
    }
}
