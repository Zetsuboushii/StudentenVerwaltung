package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class EditStudentViewController extends GenericUIController {
    @FXML
    private Button btSave;

    @FXML
    private StudentFormFieldsController studentFormFieldsController;
    public void setStudent(Student student) {
        studentFormFieldsController.setStudent(student);
    }

    public void populate() throws SQLException {
        studentFormFieldsController.populate();
    }

    @Override
    public void setVisible(boolean visible) throws Exception {
        super.setVisible(visible);
        btSave.setDisable(!visible);
        studentFormFieldsController.setVisible(visible);
    }

    @FXML private void onSave() {
        studentFormFieldsController.updateStudent();
    }
}
