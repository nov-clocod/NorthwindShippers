package com.pluralsight.DAO;

import com.pluralsight.models.Shipper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShippersDAO {
    private DataSource dataSource;

    public ShippersDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertNewShipper(String companyName, String companyPhoneNumber) {
        try {
            String query = """
                    INSERT INTO shippers (CompanyName, Phone)
                    VALUES (?, ?)
                    """;

            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
            ) {
                preparedStatement.setString(1, companyName);
                preparedStatement.setString(2, companyPhoneNumber);

                preparedStatement.executeUpdate();

                try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                    if (key.next()) {
                        System.out.println("A new key was added: " + key.getInt(1));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
    }

    public List<Shipper> displayAllShippers() {
        List<Shipper> allShippers = new ArrayList<>();

        try {
            String query = """
                    SELECT ShipperID, CompanyName, Phone
                    FROM shippers
                    """;
            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query);

                 ResultSet results = preparedStatement.executeQuery()
            ) {
                while (results.next()) {
                    int shipperID = results.getInt(1);
                    String companyName = results.getString(2);
                    String companyPhone = results.getString(3);

                    allShippers.add(new Shipper(shipperID, companyName, companyPhone));
                }
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
        return allShippers;
    }

    public void updateShipperContactNumber(String newPhoneNumber, int shipperID) {
        try {
            String query = """
                    UPDATE shippers
                    SET Phone = ?
                    WHERE ShipperID = ?
                    """;

            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setString(1, newPhoneNumber);
                preparedStatement.setInt(2, shipperID);

                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
    }

    public void deleteShipper(int shipperID) {
        try {
            String query = """
                    DELETE FROM shippers
                    WHERE ShipperID = ?
                    """;

            try (Connection connection = dataSource.getConnection();

                 PreparedStatement preparedStatement = connection.prepareStatement(query)
            ) {
                preparedStatement.setInt(1, shipperID);

                preparedStatement.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("Error occurred!");
            System.out.println(ex.getMessage());
        }
    }
}
