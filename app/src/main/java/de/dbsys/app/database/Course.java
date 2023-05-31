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

    private String cName, room;

    public Course(String cName) {
        this.cName = cName;
    }

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

    public void editCname(Connection conn, String cName) {
        try {
            String sql = "UPDATE course SET cName = ? WHERE cName = " + this.cName;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cName);
            stmt.executeUpdate();
            stmt.close();
            this.cName = cName;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public void editRoom(Connection conn, String room) {
        try {
            String sql = "UPDATE course SET room = ? WHERE cName = " + this.cName;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, room);
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
