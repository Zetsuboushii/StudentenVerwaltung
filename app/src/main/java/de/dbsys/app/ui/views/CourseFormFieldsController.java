package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class CourseFormFieldsController extends GenericUIController {
    @FXML
    private TextField tfCourseName;
    @FXML
    private TextField tfRoom;
    public Pair<String, String> getCourseData() {
        return new Pair<>(tfCourseName.getText(), tfRoom.getText());
    }

    public void setCourseData(String course, String room) {
        tfCourseName.setText(course);
        tfRoom.setText(room);
    }
}
