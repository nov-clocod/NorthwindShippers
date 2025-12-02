package com.pluralsight;

import com.pluralsight.DAO.ShippersDAO;
import com.pluralsight.models.Shipper;
import org.apache.commons.dbcp2.BasicDataSource;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Error on required application details to run");
            System.exit(1);
        }

        String username = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        try (Scanner myScanner = new Scanner(System.in)) {
            ShippersDAO shippersDAO = new ShippersDAO(dataSource);

            System.out.println("\nEnter the name of the company you want to add:");
            String userInputCompanyName = myScanner.nextLine().trim();

            System.out.println("\nEnter the phone number of the company you want to add:");
            String userInputCompanyPhoneNumber = myScanner.nextLine().trim();

            shippersDAO.insertNewShipper(userInputCompanyName, userInputCompanyPhoneNumber);

            displayAllShippers(shippersDAO);

            System.out.println("\nEnter the ID of the shipper you want to update:");
            int userInputShipperID = myScanner.nextInt();
            myScanner.nextLine();

            System.out.println("\nEnter the new phone number for the shipper:");
            String userInputNewPhoneNumber = myScanner.nextLine().trim();

            shippersDAO.updateShipperContactNumber(userInputNewPhoneNumber, userInputShipperID);

            displayAllShippers(shippersDAO);

            System.out.println("\nEnter the shipper ID to delete:");
            int userInputShipperIDToDelete = myScanner.nextInt();

            shippersDAO.deleteShipper(userInputShipperIDToDelete);

            displayAllShippers(shippersDAO);
        } catch (Exception ex) {
            System.out.println("Please check your inputs!");
            System.out.println(ex.getMessage());
        }
    }

    public static void displayAllShippers(ShippersDAO shippersDAO) {
        List<Shipper> shipperList = shippersDAO.displayAllShippers();
        if (!shipperList.isEmpty()) {
            System.out.println("\nShipper ID " + "Company Name" + " ".repeat(29) + "Phone Number");
            System.out.println("---------- " + "-".repeat(40) + " " + "-".repeat(24));
            shipperList.forEach(System.out::println);
        } else {
            System.out.println("\nThere are no shippers!");
        }
    }
}
