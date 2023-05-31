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
        Statement stmt = conn.createStatement();
        // Create necessary tables in case they don't exist
        stmt.execute(
                "create table if not exists kurs" +
                        "(" +
                        "    kName text not null" +
                        "        constraint kurs_pk" +
                        "            primary key," +
                        "    raum  text" +
                        ");"
        );
        stmt.execute(
                "create table if not exists student" +
                        "(" +
                        "    mNr        integer not null" +
                        "        constraint student_pk" +
                        "            primary key," +
                        "    name       text    not null," +
                        "    vName      text    not null," +
                        "    firma      text    not null," +
                        "    fk_kurs    text" +
                        "        constraint student_kurs_kName_fk" +
                        "            references kurs" +
                        "            on update set null on delete set null," +
                        "    javaSkills integer" +
                        ");"
        );
        stmt.close();
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
