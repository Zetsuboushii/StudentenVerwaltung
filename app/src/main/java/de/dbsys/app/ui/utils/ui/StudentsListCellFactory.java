package de.dbsys.app.ui.utils.ui;

import de.dbsys.app.database.NoCourseException;
import de.dbsys.app.database.entities.Student;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

import java.util.Objects;

public class StudentsListCellFactory implements Callback<ListView<Student>, ListCell<Student>> {
    @Override
    public ListCell<Student> call(ListView<Student> studentListView) {
        return new ListCell<>(){
            @Override
            public void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setText(null);
                } else {
                    setText(student.toString());
                    try {
                        if(student.getCourse() == null || Objects.equals(student.getCourse().getcName(), "Empty Course")) {
                            throw new NoCourseException();
                        }
                        setStyle("-fx-text-fill: #555555; -fx-font-style: italic");
                        setTooltip(new Tooltip("Aktueller Kurs: " + student.getCourse().getcName()));
                    } catch (NoCourseException ignored) {
                    }
                }
            }
        };
    }
}
