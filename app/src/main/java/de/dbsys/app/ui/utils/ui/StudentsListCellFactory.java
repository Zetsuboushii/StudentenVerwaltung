package de.dbsys.app.ui.utils.ui;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Student;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Objects;

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
                    try {
                        if(student.getCourse() == null || Objects.equals(student.getCourse().getcName(), "Empty Course")) {
                            throw new NoCourseException();
                        }
                        icon.setIconColor(new Color(1F/3, 1F/3, 1F/3, 1F));
                        setStyle("-fx-text-fill: #555555; -fx-font-style: italic");
                        setTooltip(new Tooltip("Aktueller Kurs: " + student.getCourse().getcName()));
                    } catch (NoCourseException ignored) {
                    }
                }
            }
        };
    }
}
