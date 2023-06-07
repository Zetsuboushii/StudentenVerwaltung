package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Student;

import java.util.Comparator;

public class LastNameStudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getSname().compareTo(o2.getSname());
    }

    @Override
    public String toString() {
        return "Nachname";
    }
}
