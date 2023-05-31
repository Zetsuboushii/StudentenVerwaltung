/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         Student
Date of Creation:   2023:05:31, 13:08
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.*;

public class Student {

    private int mNr;
    private String fname, sname, company;
    private Course course;
    private int javaSkill;

    public Student(int mNr, String fname, String sname, String company, int javaSkill) {
        this.mNr = mNr;
        this.fname = fname;
        this.sname = sname;
        this.company = company;
        this.javaSkill = javaSkill;
    }

    public void createStudent(Connection conn) {
        try {
            String sql = "INSERT INTO student (mNr, name, vName, firma, javaSkills) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mNr);
            stmt.setString(2, sname);
            stmt.setString(3, fname);
            stmt.setString(4, company);
            stmt.setInt(5, javaSkill);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values successfully inserted");
    }

    public void delete(Connection conn) {
        try {
            String sql = "DELETE FROM student WHERE mNr = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.mNr);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void edit_mNr(Connection conn, int mNr) {
        try {
            String sql = "UPDATE student SET mNr = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mNr);
            stmt.executeUpdate();
            stmt.close();
            this.mNr = mNr;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.getConn();
        new Student(5745312, "Richard", "Riesmeier", "DBSystel", 4).edit_mNr(conn, 6738425);
    }

}
