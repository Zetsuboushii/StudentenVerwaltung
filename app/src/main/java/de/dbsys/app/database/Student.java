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

    // TODO Crawl DB for existing objects

    private int mNr;
    private String fname, sname, company;
    private Course course;
    private int javaSkill;

    /**
     * Creates new Student Object
     * @param mNr       Matrikelnumber
     * @param fname     Firstname
     * @param sname     Surname
     * @param company   Company
     * @param javaSkill Java Skill
     */
    public Student(int mNr, String fname, String sname, String company, int javaSkill) {
        this.mNr = mNr;
        this.fname = fname;
        this.sname = sname;
        this.company = company;
        this.javaSkill = javaSkill;
    }

    /**
     * Creates new Database entry in table student w/o course reference
     * @param conn  Established DB connection
     */
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

    /**
     * Deletes Database entry in table student
     * @param conn  Established DB connection
     */
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

    /**
     * Edits matriculation no. of entry by its matriculation no. and updates Java Object values
     * @param conn  Established DB connection
     * @param mNr   Matriculation no.
     */
    public void editMnr(Connection conn, int mNr) {
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

    /**
     * Edits firstname of entry by its matriculation no. and updates Java Object values
     * @param conn  Established connection
     * @param fname Firstname
     */
    public void editFname(Connection conn, String fname) {
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

    /**
     * Edits surname of entry by its matriculation no. and updates Java Object values
     * @param conn  Established DB connection
     * @param sname Surname
     */
    public void editSname(Connection conn, String sname) {
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

    /**
     * Edits company of entry by its matriculation no. and updates Java Object values
     * @param conn      Established DB connection
     * @param company   Company
     */
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

    /**
     * Edits Java Skill of entry by its matriculation no. and updates Java Object values
     * @param conn      Established DB connection
     * @param javaSkill Java Skill
     */
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

    /**
     * Edits course of entry by its matriculation no. and updates Java Object values
     * @param conn      Established DB connection
     * @param course    Course name
     */
    public void editCourse(Connection conn, Course course) {
        try {
            String sql = "UPDATE student SET fk_course = ? WHERE mNr = " + this.mNr;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getcName());
            stmt.executeUpdate();
            stmt.close();
            this.course = course;
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Values updated.");
    }

    public int getmNr() {
        return mNr;
    }

    public String getSname() {
        return sname;
    }

    public String getFname() {
        return fname;
    }

    public String getCompany() {
        return company;
    }

    public Course getCourse() {
        return course;
    }

    public int getJavaSkill() {
        return javaSkill;
    }
}
