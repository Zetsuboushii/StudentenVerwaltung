package de.dbsys.app.ui.utils.filters;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;

import java.util.function.Predicate;

public class CourseStudentFilter implements Predicate<Student> {
    private final Course course;

    public CourseStudentFilter(Course course) {
        this.course = course;
    }

    @Override
    public boolean test(Student student) {
        try {
            return student.getCourse().getcName().equals(course.getcName());
        } catch (NoCourseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Kurs: " + course;
    }
}
