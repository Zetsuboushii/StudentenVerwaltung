package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Course;

import java.util.Comparator;

public class NameCourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        return o1.getcName().compareTo(o2.getcName());
    }

    @Override
    public String toString() {
        return "Name";
    }
}
