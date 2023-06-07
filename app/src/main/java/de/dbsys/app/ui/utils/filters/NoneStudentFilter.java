package de.dbsys.app.ui.utils.filters;

import de.dbsys.app.database.entities.Student;

import java.util.function.Predicate;

public class NoneStudentFilter implements Predicate<Student> {
    @Override
    public boolean test(Student student) {
        return true;
    }

    @Override
    public String toString() {
        return "Alle Kurse";
    }
}
