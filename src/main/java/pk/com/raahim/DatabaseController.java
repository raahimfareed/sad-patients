/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pk.com.raahim;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author yeet
 */
public class DatabaseController {
    private String username = null;
    private String password = null;
    private String host = null;
    private int port = 0;
    private String dbName = null;

    private final String tableName = "patients";

    private int offset = 0;

    public DatabaseController(String name, String host, int port, String username, String password) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.dbName = name;

        Connection conn = connect();

        if (conn == null) return;
        String query = "CREATE TABLE IF NOT EXISTS "+ tableName +" (" +
            "id INT NOT NULL PRIMARY KEY," +
            "name VARCHAR(255) NOT NULL);";

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Could not create table! Error: " + e.getMessage());
        }
    }

    private Connection connect() {
        System.out.println("Connecting to database...");
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://" + host + ":" + Integer.toString(port) + "/" + dbName;
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }

        return null;
    }

    public void insertPatient(Patient p)
    {
        Connection connection = connect();

        if (connection == null) return;

        String query = "INSERT INTO "+tableName+" (id, name) VALUES (?, ?);";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not add patient! Error: " + e.getMessage());
            return;
        }
    }

    public void deletePatient(String name)
    {
        Connection connection = connect();

        if (connection == null) return;

        String query = "DELETE FROM "+tableName+" WHERE name = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setString(1, name);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not delete patient! Error: " + e.getMessage());
            return;
        }
    }

    public void updatePatient(String oldName, int id, String name)
    {
        Connection connection = connect();

        if (connection == null) return;

        String query = "UPDATE "+tableName+" SET id = ?, name = ? WHERE name = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, oldName);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not update patient! Error: " + e.getMessage());
            return;
        }
    }

    public Patient next()
    {
        Connection connection = connect();
        String query = "SELECT * FROM "+tableName+" LIMIT 1 OFFSET ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, offset++);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Patient(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Reached end of page. Error: " + e.getMessage());
            return new Patient(0, "No patient at this index");
        }
        return Patient.Patient404();
    }

    public Patient prev()
    {
        Connection connection = connect();
        String query = "SELECT * FROM "+tableName+" LIMIT 1 OFFSET ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            if (offset < 0)
            {
                return new Patient(0, "No Patients before this");
            }

            stmt.setInt(1, --offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Patient(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println("Reached end of page. Error: " + e.getMessage());
            return new Patient(0, "No patient at this index");
        }
        return Patient.Patient404();
    }

    public Patient findPatient(String name)
    {
        Connection connection = connect();
        if (connection == null) return Patient.Patient404();

        String query = "SELECT * FROM "+tableName+" WHERE name = ? LIMIT 1;";

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            rs.last();
            if (rs.getRow() < 1) {
                return Patient.Patient404();
            }

            return new Patient(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) {
            System.out.println("Could not retrieve users! Error: " + e.getMessage());
            return new Patient(0, "Could not get patients");
        }
    }

    public Patient firstPatient()
    {
        Connection connection = connect();
        if (connection == null) return Patient.Patient404();

        String query = "SELECT * FROM "+tableName+" LIMIT 1;";

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            offset = 0;

            ResultSet rs = stmt.executeQuery();
            rs.last();
            if (rs.getRow() < 1) {
                return Patient.Patient404();
            }

            return new Patient(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) {
            System.out.println("Could not retrieve users! Error: " + e.getMessage());
            return new Patient(0, "Could not get patients");
        }
    }

    public Patient lastPatient()
    {
        return Patient.Patient404();
    }
}
