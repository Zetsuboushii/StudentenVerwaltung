package de.dbsys.app.ui.views;

import de.dbsys.app.database.Course;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class StudentFormFieldsController extends GenericUIController {
    @FXML
    private TextField tfFirstName;
    @FXML
    private TextField tfLastName;
    @FXML
    private ComboBox<Course> cbClass;
    @FXML
    private TextField tfCompany;
    @FXML
    private Slider slJavaExp;
}
