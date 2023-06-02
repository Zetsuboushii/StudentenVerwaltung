/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DatabaseCrawler
Date of Creation:   2023:06:02, 13:05
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseCrawler {

    public ArrayList<Student> selectAllStudents(Connection conn) throws SQLException {
        ArrayList<Student> ex_students = new ArrayList<>();

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
            if (course != null) {
                ex_students.add(new Student(mNr, sname, fname, company, course, javaSkill));
            } else {
                ex_students.add(new Student(mNr, sname, fname, company, javaSkill));
            }
            System.out.println("Received data for student " + mNr + "\n");
        }

        return ex_students;
    }

    public ArrayList<Course> selectAllCourses(Connection conn) throws SQLException {
        ArrayList<Course> ex_courses = new ArrayList<>();

        String sql = "SELECT cname, room FROM course";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        // Loop through the result set
        while (rs.next()) {
            String cName = rs.getString("cname");
            String room = rs.getString("room");
            if (room != null) {
                ex_courses.add(new Course(cName, room));
            } else {
                ex_courses.add(new Course(cName));
            }
            System.out.println("Received data for course " + cName + "\n");
        }

        return ex_courses;
    }
}