package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Course;

import java.util.Comparator;

/**
 * Sorts courses by room number.
 * Example: 017B > 065A > 189A > 315C > 315B
 */
public class RoomCourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        if(o1.getRoom() == null || o2.getRoom() == null) {
            return o1.getRoom() == null ? -1 : 1;
        }
        return o1.getRoom().compareTo(o2.getRoom());
    }

    @Override
    public String toString() {
        return "Raum";
    }
}
