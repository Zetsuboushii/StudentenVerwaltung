/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DBTester
Date of Creation:   2023:05:31, 15:45
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DBTester {

    public static void main(String[] args) throws SQLException, NoCourseException {

        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.getConn();

        conn.createStatement().execute("DELETE FROM course");
        conn.createStatement().execute("DELETE FROM student");

        Student s1 = new Student(1234567, "Nick", "Büttner", "DB Systel", 6);
        s1.createStudent(dbc, conn);
        Student s2 = new Student(1357924, "Richard", "Riesmeier", "DB Systel", 4);
        s2.createStudent(dbc, conn);

        s1.editFname(dbc, "Knickemann");

        Course c1 = new Course("TINF22AI1", "185C");
        c1.createCourse(dbc, conn);

        s1.editCourse(dbc, c1);
        s2.editCourse(dbc, c1);

        System.out.println(s1.getmNr() + ": " + s1.getSname() + " " + s1.getFname() + " " + "\n\tKurs: " + s1.getCourse().getcName());
        System.out.println(s2.getmNr() + ": " + s2.getSname() + " " + s2.getFname() + " " + "\n\tKurs: " + s2.getCourse().getcName());

        List<Student> listofc0students = c1.getStudents(conn);
        System.out.println("Students in c0");
        for (Student s : listofc0students) {
            System.out.println(s.getmNr() + ": " + s.getSname() + " " + s.getFname() + " " + "\n\tKurs: " + s.getCourse().getcName());
        }

        System.out.println(c1.getcName() + ": " + c1.getRoom());
        c1.editRoom(dbc, "200C");
        System.out.println(c1.getcName() + ": " + c1.getRoom());

        c1.editCname(dbc, "Tinfo");
        System.out.println(c1.getcName() + ": " + c1.getRoom());

        listofc0students = c1.getStudents(conn);
        System.out.println("Students in c0");
        for (Student s : listofc0students) {
            System.out.println(s.getmNr() + ": " + s.getSname() + " " + s.getFname() + " " + "\n\tKurs: " + s.getCourse().getcName());
        }

        s2.deleteStudent(dbc);

        s1.removeCourse(dbc);

        boolean exHappend = false;
        try {
            System.out.println(s1.getmNr() + ": " + s1.getSname() + " " + s1.getFname() + " " + "\n\tKurs: " + s1.getCourse().getcName());
        } catch (NoCourseException e) {
            exHappend = true;
        }
        if (!exHappend) {
            throw new RuntimeException("NO COURSE EXCEPTION NOT TRIGGERED");
        }

        conn.close();
    }
}
