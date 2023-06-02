/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         DBTester
Date of Creation:   2023:05:31, 15:45
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTester {

    public static void main(String[] args) throws SQLException {
        DatabaseConnector dbc = new DatabaseConnector();
        Connection conn = dbc.getConn();



        Student s1 = new Student(1234567, "Nick", "Büttner", "DB Systel", 6);
        s1.createStudent(conn);
        Student s2 = new Student(1357924, "Richard", "Riesmeier", "DB Systel", 4);
        s2.createStudent(conn);

        s1.editFname(conn, "Knickemann");

        Course c1 = new Course("TINF22AI1","185C");
        c1.createCourse(conn);

        s1.editCourse(conn, c1);
        s2.editCourse(conn, c1);

        System.out.println(s1.getmNr() + ": " + s1.getSname() + " " + s1.getFname() + " " + "\n\tKurs: " + s1.getCourse().getcName());
        System.out.println(s2.getmNr() + ": " + s2.getSname() + " " + s2.getFname() + " " + "\n\tKurs: " + s2.getCourse().getcName());

        System.out.println(c1.getcName() + ": " + c1.getRoom());
        c1.editRoom(conn, "200C");
        System.out.println(c1.getcName() + ": " + c1.getRoom());

        c1.editCname(conn,"Tinfo");
        System.out.println(c1.getcName() + ": " + c1.getRoom());

        s2.delete(conn);

        conn.close();
    }
}
