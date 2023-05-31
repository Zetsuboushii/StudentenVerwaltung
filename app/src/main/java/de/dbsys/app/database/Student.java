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
            String sql = "INSERT INTO student (mNr, sname, fname, company, javaSkill) VALUES (?, ?, ?, ?, ?)";
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
        System.out.println("Values successfully inserted.");
    }

    public void delete(Connection conn) {
        try {
            String sql = "DELETE FROM student WHERE mNr = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, this.mNr);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Student successfully deleted.");
    }

    public void editmNr(Connection conn, int mNr) {
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

    public void editfname(Connection conn, String fname) {
        try {
            String sql = "UPDATE student SET fname = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, fname);
            stmt.executeUpdate();
            stmt.close();
            this.fname = fname;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public void editsname(Connection conn, String sname) {
        try {
            String sql = "UPDATE student SET sname = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, sname);
            stmt.executeUpdate();
            stmt.close();
            this.sname = sname;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public void editCompany(Connection conn, String company) {
        try {
            String sql = "UPDATE student SET company = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company);
            stmt.executeUpdate();
            stmt.close();
            this.company = company;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public void editJavaSkill(Connection conn, int javaSkill) {
        try {
            String sql = "UPDATE student SET javaSkill = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, javaSkill);
            stmt.executeUpdate();
            stmt.close();
            this.javaSkill = javaSkill;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }
}
