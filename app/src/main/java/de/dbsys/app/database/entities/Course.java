/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         Course
Date of Creation:   2023:05:31, 13:13
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database.entities;

import de.dbsys.app.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                this.editRoom(conn, room);
            }
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values successfully inserted.");
    }

    /**
     * Deletes Database entry in table course
     * @param dbc
     */
    public void deleteCourse(DatabaseConnector dbc) {
        String sql = "DELETE FROM course WHERE cname = ?";
        dbc.delete(sql, this.cName);
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
    public void editRoom(Connection conn, String room) {
        String sql = "UPDATE course SET room = ? WHERE cName IS ?";

        this.room = room;
    }

    public String getcName() {
        if (cName == null) {
            return "Empty Course";
        }
        return cName;
    }

    public String getRoom() {
        return room;
    }

    public List<Student> getStudents(Connection conn) throws SQLException{
        String sql = "SELECT mNr, sname, fname, company, fk_course, javaSkill FROM student WHERE fk_course = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,cName);
        ResultSet rs = stmt.executeQuery();

        List<Student> l = new ArrayList<>();

        // Loop through the result set
        while (rs.next()) {
            int mNr = rs.getInt("mNr");
            String sname = rs.getString("sname");
            String fname = rs.getString("fname");
            String company = rs.getString("company");
            Course course = new Course(rs.getString("fk_course"));
            int javaSkill = rs.getInt("javaSkill");
            l.add(new Student(mNr, sname, fname, company, course, javaSkill));
        }

        return l;

    }
}