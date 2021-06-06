package nl.hu.bep.demo.ShopList.webservices;

import com.sun.jdi.connect.spi.Connection;

import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;
import java.util.Properties;
import java.sql.*;
import java.sql.DriverManager;

import java.sql.DriverManager;


public class Main {
    public static void main(String[] args) {

//        final String url = "jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7";
//        final String user = "lnwttavtayzuha";
//        final String password = "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303";

//        try {
//            Class.forName("org.postgresql.Driver");
////            Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-34-247-118-233.eu-west-1.compute.amazonaws.com:5432/d1g463l0023r2h","ffvrfkbyrrgfus", "656850ab0d2721b29fb28079cd32656062ea612906db3673b609e5265000");
//            Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha","3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
//            Statement stmt = conn.createStatement();
//            ResultSet afsp = stmt.executeQuery("SELECT * FROM afspraak");
//            while(afsp.next()){
//                Date datum = afsp.getDate(2);
//                Time tijd = afsp.getTime(3);
//                String naam = afsp.getString(4);
//                String email = afsp.getString(5);
//
//
//            }
//            afsp.close();
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
        try {
//            String url = "jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7";
//            Properties props = new Properties();
//            props.setProperty("lnwttavtayzuha","postgres");
//            props.setProperty("password","3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");
//            Connection conn = (Connection) DriverManager.getConnection(url, props);
//
//            Statement is =
            Class.forName("org.postgresql.Driver");

            java.sql.Connection conn = DriverManager.getConnection("jdbc:postgresql://ec2-54-155-35-88.eu-west-1.compute.amazonaws.com:5432/d58m0v63ch5ib7", "lnwttavtayzuha", "3c941bd547c6a272b4b91d6388ca27d1524c2d59e08d6d3993f0e00b93753303");

            java.sql.Statement stmt = conn.createStatement();



//            ResultSet braka = stmt.executeQuery("insert into item(itemnaam) values('Bob')");
//            ResultSet rsHuidigeStudent = stmt.executeQuery("select * from item");
            ResultSet rsUser = stmt.executeQuery("insert into gebruiker(username, email, wachtwoord, rol) values('stijn', 'stijn@stijn.com', 'stijn', 'user')");

            while (rsUser.next()) {
                String naam = rsUser.getString(2);
                System.out.println(naam);
            }





        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
