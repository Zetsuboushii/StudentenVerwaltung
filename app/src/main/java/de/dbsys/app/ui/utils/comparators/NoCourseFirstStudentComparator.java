package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Student;

import java.util.Comparator;
import java.util.Objects;

public class NoCourseFirstStudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        boolean s1HasCourse = true;
        boolean s2HasCourse = true;
        try {
            // TODO: i shouldn't need this (if the database worked...)
            if(s1.getCourse() == null || Objects.equals(s1.getCourse().getcName(), "Empty Course")) {
                throw new NoCourseException();
            }
        } catch (NoCourseException e) {
            s1HasCourse = false;
        }

        try {
            // TODO: i shouldn't need this (if the database worked...)
            if(s2.getCourse() == null || Objects.equals(s2.getCourse().getcName(), "Empty Course")) {
                throw new NoCourseException();
            }
        } catch (NoCourseException e) {
            s2HasCourse = false;
        }

        if(s1HasCourse && !s2HasCourse) {
            return 1;
        } else if(!s1HasCourse && s2HasCourse) {
            return -1;
        } else {
            return s1.getFname().compareTo(s2.getFname());
        }
    }
}
