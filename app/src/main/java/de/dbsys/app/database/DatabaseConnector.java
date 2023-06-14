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

    private static Connection conn;

    public DatabaseConnector() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:app/public/studentDB.db");

        String courseDDL =
                """
                create table if not exists course
                (
                    cName text not null
                        constraint course_pk
                            primary key
                        unique,
                    room  text
                );
                """;
        String studentDDL =
                """
                         create table if not exists student
                        (
                            mNr       integer not null
                                constraint student_pk
                                    primary key
                                unique,
                            sname     text    not null,
                            fname     text    not null,
                            company   text    not null,
                            fk_course text default null
                                constraint student_course_cName_fk
                                    references course
                                    on update cascade on delete set null,
                            javaSkill integer
                        );
                """;



        conn.createStatement().execute("PRAGMA foreign_keys = ON");
        conn.createStatement().execute(courseDDL);
        conn.createStatement().execute(studentDDL);

        System.out.println("Connection established.");
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        } else {
            throw new SQLException("Connection is null.");
        }
    }

    // TODO: remove redundant return values
    public boolean update(String sql, Object pk, Object val) throws SQLException {
            PreparedStatement stmt = conn.prepareStatement(sql);

            if (val instanceof String) {
                stmt.setString(1, (String) val);
            } else if (val instanceof Integer) {
                stmt.setInt(1, (Integer) val);
            }

            if (pk instanceof Integer) {
                stmt.setInt(2, (int) pk);
            } else if (pk instanceof String) {
                stmt.setString(2, (String) pk);
            }

            stmt.executeUpdate();
            stmt.close();
        System.out.println("Values updated.");
        return true;
    }

    public boolean delete(String sql, Object pk) throws SQLException {
            PreparedStatement stmt = conn.prepareStatement(sql);
            if (pk instanceof Integer) {
                stmt.setInt(1, (int) pk);
            } else if (pk instanceof String) {
                stmt.setString(1, (String) pk);
            }
            stmt.executeUpdate();
            stmt.close();
        System.out.println("Student successfully deleted.");
        return true;
    }

    public Connection getConn() {
        return conn;
    }

    public void main(String[] args) {

    }

}
