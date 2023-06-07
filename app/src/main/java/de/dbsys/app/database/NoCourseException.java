/*--------------------------------------------------/
Project Name:       StudentenVerwaltung
Class Name:         NoCourseException
Date of Creation:   2023:06:07, 11:06
Used IDE:           IntelliJ IDEA
Author:             Luke Grasser
/--------------------------------------------------*/

package de.dbsys.app.database;

public class NoCourseException extends Exception {

    public NoCourseException(){
        super("Student has no course");
    }

}
