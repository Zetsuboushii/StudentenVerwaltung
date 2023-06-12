package de.dbsys.app.ui.utils.ui;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

public class CourseListCellFactory implements Callback<ListView<Course>, ListCell<Course>> {
    @Override
    public ListCell<Course> call(ListView<Course> studentListView) {
        return new ListCell<>() {
            @Override
            public void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setText(null);
                } else {
                    FontIcon icon = new FontIcon("fas-users");
                    setGraphic(icon);
                    setText(course.toString());
                }
            }
        };
    }
}
