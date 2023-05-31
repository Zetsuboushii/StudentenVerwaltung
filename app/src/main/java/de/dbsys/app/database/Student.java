/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         Student
Date of Creation:   2023:05:31, 13:08
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

public class Student {

    DatabaseConnector dbc = new DatabaseConnector();

    private String fname, sname, company;
    private Course course;
    private byte javaSkill;

    public Student(String fname, String sname, String company) {
        this.fname = fname;

    }

    public static void create(){

    }

    public static void edit() {

    }

    public static void delete() {

    }

}
