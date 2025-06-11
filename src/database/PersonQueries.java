package database;

import model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonQueries {
    private static final String URL = "jdbc:mysql://localhost:3306/AddressBook";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Admin123";

    private Connection connection;
    private PreparedStatement selectAllPeople;
    private PreparedStatement selectPeopleByLastName;
    private PreparedStatement insertNewPerson;

    public PersonQueries() {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Query to select all people
            selectAllPeople = connection.prepareStatement(
                "SELECT * FROM Addresses ORDER BY LastName, FirstName");

            // Query to select people by last name
            selectPeopleByLastName = connection.prepareStatement(
                "SELECT * FROM Addresses WHERE LastName LIKE ? ORDER BY LastName, FirstName");

            // Query to insert a new person
            insertNewPerson = connection.prepareStatement(
                "INSERT INTO Addresses (FirstName, LastName, Email, PhoneNumber, Address, Department) " +
                "VALUES (?, ?, ?, ?, ?, ?)");

        } catch (ClassNotFoundException | SQLException e) {
            logError(e);
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    // Get all people
    public List<Person> getAllPeople() {
        try (ResultSet resultSet = selectAllPeople.executeQuery()) {
            List<Person> results = new ArrayList<>();
            while (resultSet.next()) {
                results.add(mapToPerson(resultSet));
            }
            return results;
        } catch (SQLException e) {
            logError(e);
            return new ArrayList<>(); // Return empty list on error
        }
    }

    // Get people by last name
    public List<Person> getPeopleByLastName(String lastName) {
        try {
            selectPeopleByLastName.setString(1, lastName + "%");
            try (ResultSet resultSet = selectPeopleByLastName.executeQuery()) {
                List<Person> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(mapToPerson(resultSet));
                }
                return results;
            }
        } catch (SQLException e) {
            logError(e);
            return new ArrayList<>(); // Return empty list on error
        }
    }

    // Add a person
    public int addPerson(String firstName, String lastName, String email, String phoneNumber, String address, String department) {
        try {
            insertNewPerson.setString(1, firstName);
            insertNewPerson.setString(2, lastName);
            insertNewPerson.setString(3, email);
            insertNewPerson.setString(4, phoneNumber);
            insertNewPerson.setString(5, address);
            insertNewPerson.setString(6, department);

            return insertNewPerson.executeUpdate();
        } catch (SQLException e) {
            logError(e);
            return 0; // Return 0 on failure
        }
    }

    // Map ResultSet to Person
    private Person mapToPerson(ResultSet resultSet) throws SQLException {
        return new Person(
            resultSet.getInt("AddressID"),
            resultSet.getString("FirstName"),
            resultSet.getString("LastName"),
            resultSet.getString("Email"),
            resultSet.getString("PhoneNumber"),
            resultSet.getString("Address"),
            resultSet.getString("Department")
        );
    }

    // Log error (could use a logger here)
    private void logError(Exception e) {
        System.err.println("Error: " + e.getMessage());
        e.printStackTrace();
    }

    // Close the connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logError(e);
        }
    }
}
