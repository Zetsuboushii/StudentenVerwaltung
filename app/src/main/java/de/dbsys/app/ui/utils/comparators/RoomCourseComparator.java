package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Course;

import java.util.Comparator;

public class RoomCourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        return o1.getRoom().compareTo(o2.getRoom());
    }

    @Override
    public String toString() {
        return "Raum";
    }
}
