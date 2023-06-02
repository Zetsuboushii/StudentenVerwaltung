/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         Course
Date of Creation:   2023:05:31, 13:13
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Course {

    // TODO Crawl DB for existing objects

    private String cName, room;

    public Course(String cName) {
        this.cName = cName;
    }

    /**
     * creates a new database entry in table course
     * @param conn  Established DB connection
     */
    public void createCourse(Connection conn) {
        try {
            String sql = "INSERT INTO course (cName) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cName);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values successfully inserted.");
    }

    /**
     * Edits course name of entry by its course name and updates java object values
     * @param conn
     * @param cName
     */
    public void editCname(Connection conn, String cName) {
        try {
            String sql = "UPDATE course SET cName = ? WHERE cName IS ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cName);
            stmt.setString(2, this.cName);
            stmt.executeUpdate();
            stmt.close();
            this.cName = cName;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    /**
     * Edits room of entry by its course name java object values
     * @param conn
     * @param room
     */
    public void editRoom(Connection conn, String room) {
        try {
            String sql = "UPDATE course SET room = ? WHERE cName IS ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, room);
            stmt.setString(2, this.cName);
            stmt.executeUpdate();
            stmt.close();
            this.room = room;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public String getcName() {
        return cName;
    }

    public String getRoom() {
        return room;
    }
}