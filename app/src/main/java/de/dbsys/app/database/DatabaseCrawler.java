/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DatabaseCrawler
Date of Creation:   2023:06:02, 13:05
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCrawler {

    private ArrayList<Student> ex_students = new ArrayList<>();
    private ArrayList<Course> ex_courses = new ArrayList<>();

    public void selectAll(Connection conn) throws SQLException {
        String sql = "SELECT mNr, sname, fname, company, fk_course, javaSkill FROM student";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Loop through the result set
        while (rs.next()) {
            int mNr = rs.getInt("mNr");
            String sname = rs.getString("sname");
            String fname = rs.getString("fname");
            String company = rs.getString("company");
            Course course = new Course(rs.getString("fk_course"));
            int javaSkill = rs.getInt("javaSkill");
            if (company != null) {
                ex_students.add(new Student(mNr, sname, fname, company, course, javaSkill));
            } else {
                ex_students.add(new Student(mNr, sname, fname, company, javaSkill));
            }
            System.out.println("Received data for student " + mNr + "\n");
        }

    }
}