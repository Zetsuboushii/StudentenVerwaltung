package de.dbsys.app.ui.utils.comparators;

import de.dbsys.app.database.entities.Course;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DHBWRoomCourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course o1, Course o2) {
        boolean match1 = matches(o1.getRoom(), "^\\d+[A-Z]$");
        boolean match2 = matches(o2.getRoom(), "^\\d+[A-Z]$");
        if(match1 && match2) {
            String building1 = o1.getRoom().substring(o1.getRoom().length() - 1);
            String building2 = o2.getRoom().substring(o2.getRoom().length() - 1);
            int result = building1.compareTo(building2);
            if(result == 0) {
                return o1.getRoom().compareTo(o2.getRoom());
            }
            return result;
        }
        if(match1) {
            return -1;
        }
        if(match2) {
            return 1;
        }
        return 0;
    }

    public static boolean matches(String testString, String pattern) {
        if(testString == null || pattern == null) {
            return false;
        }
        final Pattern patter = Pattern.compile(pattern);
        final Matcher matcher = patter.matcher(testString);
        return matcher.find();
    }

    @Override
    public String toString() {
        return "Gebäude (DHBW)";
    }
}
