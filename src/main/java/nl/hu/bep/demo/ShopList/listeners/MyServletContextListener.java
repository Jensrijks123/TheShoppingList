package nl.hu.bep.demo.ShopList.listeners;

import nl.hu.bep.demo.ShopList.model.Boodschappenlijstje;
import nl.hu.bep.demo.ShopList.model.Item;
import nl.hu.bep.demo.ShopList.model.User;
import nl.hu.bep.demo.ShopList.persistence.PersistenceManager;
import nl.hu.bep.demo.ShopList.security.SecurityManager;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.HttpResources;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.Duration;

import static java.lang.System.*;


@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Initializing application");

        User u = new User("Djensman", "jens.rijks@student.hu.nl", "Password", "admin");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Terminating application");


        Schedulers.shutdownNow();
        HttpResources.disposeLoopsAndConnectionsLater(Duration.ZERO, Duration.ZERO).block();
    }
}
