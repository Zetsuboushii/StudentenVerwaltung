package de.dbsys.app.ui.utils.filters;

import de.dbsys.app.database.entities.Student;

import java.util.function.Predicate;

/**
 * Filters students that have no course.
 */
public class NoCourseStudentFilter implements Predicate<Student> {
    @Override
    public boolean test(Student student) {
        return student.getCourse() == null;
    }

    @Override
    public String toString() {
        return "Kein Kurs";
    }
}
