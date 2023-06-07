package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Student;

import java.util.Comparator;

public class FirstNameStudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getFname().compareTo(o2.getFname());
    }

    @Override
    public String toString() {
        return "Vorname";
    }
}
