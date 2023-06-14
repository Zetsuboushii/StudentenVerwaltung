package de.dbsys.app.ui.utils.filters;

import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;

import java.util.function.Predicate;

/**
 * Filters students by a specified course.
 */
public class CourseStudentFilter implements Predicate<Student> {
    private final Course course;

    public CourseStudentFilter(Course course) {
        this.course = course;
    }

    @Override
    public boolean test(Student student) {
        return student.getCourse() != null && student.getCourse().getcName().equals(course.getcName());
    }

    @Override
    public String toString() {
        return "Kurs: " + course;
    }
}
