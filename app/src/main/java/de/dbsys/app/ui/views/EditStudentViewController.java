package de.dbsys.app.ui.views;

import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.SQLException;

public class EditStudentViewController extends GenericUIController {
    @FXML
    private Button btSave;
    @FXML
    private Button btDelete;

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
        btDelete.setDisable(!visible);
        studentFormFieldsController.setVisible(visible);
    }

    @FXML private void onSave() {
        studentFormFieldsController.updateStudent();
    }

    @FXML private void onDelete() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Soll der Student wirklich gelöscht werden?");
        confirm.showAndWait();
        if(confirm.getResult().getButtonData().isCancelButton()) {
            return;
        }
        try {
            studentFormFieldsController.deleteStudent();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Löschen des Studenten!\n" + e.getMessage()).show();
        }
        Main.getMainController().reload();
    }
}
