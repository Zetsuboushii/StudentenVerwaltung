package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class EditStudentViewController extends EditController {
    @FXML
    private VBox root;
    @FXML
    private StudentFormFieldsController studentFormFieldsController;

    public void setStudent(Student student) {
        studentFormFieldsController.setStudent(student);
    }

    @Override
    protected void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }

    @Override
    protected FormFieldsController getFormFieldsController() {
        return studentFormFieldsController;
    }
}
