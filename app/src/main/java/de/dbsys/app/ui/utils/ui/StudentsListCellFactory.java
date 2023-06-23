package de.dbsys.app.ui.utils.ui;

import de.dbsys.app.database.entities.Student;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Creates list items for student list. Can visually indicate if a student is in a course.
 */
public class StudentsListCellFactory implements Callback<ListView<Student>, ListCell<Student>> {
    private final boolean indicateCourse;

    public StudentsListCellFactory(boolean indicateCourse) {
        this.indicateCourse = indicateCourse;
    }

    @Override
    public ListCell<Student> call(ListView<Student> studentListView) {
        return new ListCell<>(){
            @Override
            public void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    FontIcon icon = new FontIcon("fas-user");
                    setGraphic(icon);
                    setText(student.toString());
                    if(!indicateCourse) {
                        return;
                    }
                    if(student.getCourse() != null) {
                        setText(student + " (aus: " + student.getCourse().getcName() + ")");
                        icon.setIconColor(new Color(1F/3, 1F/3, 1F/3, 1F));
                        setStyle("-fx-text-fill: #555555; -fx-font-style: italic");
                    }
                }
            }
        };
    }
}
