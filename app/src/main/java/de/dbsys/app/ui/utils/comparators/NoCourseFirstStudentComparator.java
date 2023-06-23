package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Student;

import java.util.Comparator;

/**
 * Sorts students alphabetically by fist name and displays students with no course first.
 */
public class NoCourseFirstStudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        boolean s1HasCourse = s1.getCourse() != null;
        boolean s2HasCourse = s2.getCourse() != null;

        if(s1HasCourse && !s2HasCourse) {
            return 1;
        } else if(!s1HasCourse && s2HasCourse) {
            return -1;
        } else {
            return s1.getFname().compareTo(s2.getFname());
        }
    }
}
