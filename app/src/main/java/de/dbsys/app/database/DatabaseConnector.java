/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DatabaseConnector
Date of Creation:   2023:05:31, 12:36
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.*;
import java.util.Stack;

public class DatabaseConnector {

    private static Connection conn;

    public DatabaseConnector() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:app/public/studentDB.db");
        System.out.println("Connection established.");
    }

    public void close(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        } else {
            throw new SQLException("Connection is null.");
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void main(String[] args) {

    }

}
