/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         Student
Date of Creation:   2023:05:31, 13:08
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database.entities;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.database.NoCourseException;

import java.sql.*;

public class Student {

    // On creating a new student, the reference to course can be set afterwards.

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

    public Student(int mNr, String fname, String sname, String company, Course course, int javaSkill) {
        this.mNr = mNr;
        this.fname = fname;
        this.sname = sname;
        this.company = company;
        this.course = course;
        this.javaSkill = javaSkill;
    }

    /**
     * Creates new Database entry in table student. If course is set prior, the function also invokes editCourse().
     *
     * @param conn Established DB connection
     */
    public void createStudent(DatabaseConnector dbc, Connection conn) {
        try {
            String sql = "INSERT INTO student (mNr, sname, fname, company, javaSkill, fk_course) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, mNr);
            stmt.setString(2, sname);
            stmt.setString(3, fname);
            stmt.setString(4, company);
            stmt.setInt(5, javaSkill);
            stmt.setString(6, null);
            stmt.executeUpdate();
            stmt.close();
            if (course != null) {
                this.editCourse(dbc, course);
            }
        } catch (SQLException e) {
            System.err.println(this.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Values successfully inserted.");
    }

    /**
     * Deletes Database entry in table student
     *
     * @param dbc Established DBC
     */
    public void deleteStudent(DatabaseConnector dbc) {
        String sql = "DELETE FROM student WHERE mNr = ?";
        dbc.delete(sql, this.mNr);
    }

    /**
     * Edits matriculation no. of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc Established DBC
     * @param mNr Matriculation no.
     */
    public void editMnr(DatabaseConnector dbc, int mNr) {
        String sql = "UPDATE student SET mNr = ? WHERE mNr = ?";
        dbc.update(sql, this.mNr, mNr);
        this.mNr = mNr;
    }

    /**
     * Edits firstname of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc   Established DBC
     * @param fname Firstname
     */
    public void editFname(DatabaseConnector dbc, String fname) {
        String sql = "UPDATE student SET fname = ? WHERE mNr = ?";
        dbc.update(sql, mNr, fname);
        this.fname = fname;
    }

    /**
     * Edits surname of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc   Established DBC
     * @param sname Surname
     */
    public void editSname(DatabaseConnector dbc, String sname) {
        String sql = "UPDATE student SET sname = ? WHERE mNr = ?";
        dbc.update(sql, mNr, sname);
        this.sname = sname;
    }

    /**
     * Edits company of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc     Established DBC
     * @param company Company
     */
    public void editCompany(DatabaseConnector dbc, String company) {
        String sql = "UPDATE student SET company = ? WHERE mNr = ?";
        dbc.update(sql, mNr, company);
        this.company = company;
    }

    /**
     * Edits Java Skill of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc       Established DBC
     * @param javaSkill Java Skill
     */
    public void editJavaSkill(DatabaseConnector dbc, int javaSkill) {
        String sql = "UPDATE student SET javaSkill = ? WHERE mNr = ?";
        dbc.update(sql, mNr, javaSkill);
        this.javaSkill = javaSkill;
    }

    /**
     * Edits course of entry by its matriculation no. and updates Java Object values
     *
     * @param dbc    Established DB connector
     * @param course Course name
     */
    public void editCourse(DatabaseConnector dbc, Course course) {
        String sql = "UPDATE student SET fk_course = ? WHERE mNr = ?";
        dbc.update(sql, mNr, course.getcName());
        this.course = course;
    }

    public void removeCourse(DatabaseConnector dbc) {
        String sql = "UPDATE student SET fk_course = ? WHERE mNr = ?";
        dbc.update(sql,mNr,null);
        this.course = null;
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

    public Course getCourse() throws NoCourseException {
        if (course == null) {
            throw new NoCourseException();
        }
        return course;
    }

    public int getJavaSkill() {
        return javaSkill;
    }
}
