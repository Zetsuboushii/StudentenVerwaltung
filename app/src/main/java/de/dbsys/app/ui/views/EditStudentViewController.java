package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class EditStudentViewController extends GenericUIController {
    @FXML
    private VBox root;
    @FXML
    private Button btSave;
    @FXML
    private Button btDelete;

    @FXML
    private StudentFormFieldsController studentFormFieldsController;
    public void setStudent(Student student) {
        studentFormFieldsController.setStudent(student);
    }

    /**
     * Populate the view with the student's data
     * @throws SQLException if there's an error loading the data from the DB.
     */
    public void populate() throws SQLException {
        studentFormFieldsController.populate();
    }

    @Override
    public void setVisible(boolean visible) throws Exception {
        super.setVisible(visible);
        btSave.setDisable(!visible);
        btDelete.setDisable(!visible);
        studentFormFieldsController.setVisible(visible);
    }

    /**
     * Save the students' data.
     */
    @FXML private void onSave() {
        studentFormFieldsController.updateStudent();
    }

    /**
     * Delete the student.
     */
    @FXML private void onDelete() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Soll der Student wirklich gelöscht werden?");
        confirm.showAndWait();
        if(confirm.getResult().getButtonData().isCancelButton()) {
            return;
        }
        try {
            studentFormFieldsController.deleteStudent();
        } catch (SQLException e) {
            handleException(e, "Fehler beim Löschen des Studenten: ");
        }
        Main.getMainController().reload();
    }

    @Override
    protected void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
