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

    // On creating a new course, the room can be set afterwards.
    private String cName, room;

    public Course(String cName) {
        this.cName = cName;
    }

    public Course(String cName, String room) {
        this.cName = cName;
        this.room = room;
    }

    /**
     * creates a new database entry in table course. If room is set prior, the function also invokes editRoom().
     *
     * @param conn Established DB connection
     */
    public void createCourse(DatabaseConnector dbc, Connection conn) {
        try {
            String sql = "INSERT INTO course (cName) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cName);
            stmt.executeUpdate();
            stmt.close();
            if (room != null) {
                this.editRoom(dbc, room);
            }
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values successfully inserted.");
    }

    /**
     * Edits course name of entry by its course name and updates java object values
     *
     * @param dbc
     * @param cName
     */
    public void editCname(DatabaseConnector dbc, String cName) {
        String sql = "UPDATE course SET cName = ? WHERE cName IS ?";
        dbc.update(sql, this.cName, cName);
        this.cName = cName;
    }

    /**
     * Edits room of entry by its course name java object values
     *
     * @param dbc
     * @param room
     */
    public void editRoom(DatabaseConnector dbc, String room) {
        String sql = "UPDATE course SET room = ? WHERE cName IS ?";
        dbc.update(sql, cName, room);
        this.room = room;
    }

    public String getcName() {
        return cName;
    }

    public String getRoom() {
        return room;
    }
}