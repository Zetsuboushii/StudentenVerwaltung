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

    /**
     * Establishes new SQL Driver Connection via JDBC's SQLite
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        conn = DriverManager.getConnection("jdbc:sqlite:public/studentDB.db");

        // In case main tables don't exist, create them
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

    /**
     * Closes previously established connection
     *
     * @throws SQLException
     */
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        } else {
            throw new SQLException("Connection is null.");
        }
    }

    /**
     * Outsourced Update method to alter various data in tables
     *
     * @param sql                       SQL statement
     * @param pk                        Primary Key of certain table
     * @param val                       Updated value
     * @throws SQLException
     */
    public void update(String sql, Object pk, Object val) throws SQLException {
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
    }

    /**
     * Outsourced Delete method to delete rows in tables
     *
     * @param sql               SQL statement
     * @param pk                Primary Key of certain table
     * @throws SQLException
     */
    public void delete(String sql, Object pk) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);
        if (pk instanceof Integer) {
            stmt.setInt(1, (int) pk);
        } else if (pk instanceof String) {
            stmt.setString(1, (String) pk);
        }
        stmt.executeUpdate();
        stmt.close();
    }

    public Connection getConn() {
        return conn;
    }

}
