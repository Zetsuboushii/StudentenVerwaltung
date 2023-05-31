/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DatabaseConnector
Date of Creation:   2023:05:31, 12:36
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.*;

public class DatabaseConnector {

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:../../../public/studentDB");
            System.out.println("Connection established.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn == null) {
                new SQLException().getMessage();
                return null;
            }
            return conn;
        }
    }

    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        connect();
    }

}
