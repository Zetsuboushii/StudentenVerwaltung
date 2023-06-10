package de.dbsys.app.ui.utils.filters;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Student;

import java.util.Objects;
import java.util.function.Predicate;

public class NoCourseStudentFilter implements Predicate<Student> {
    @Override
    public boolean test(Student student) {
        try {
            // TODO: i shouldn't need this (if the database worked...)
            if(student.getCourse() == null || Objects.equals(student.getCourse().getcName(), "Empty Course")) {
                throw new NoCourseException();
            }
        } catch (NoCourseException e) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Kein Kurs";
    }
}
