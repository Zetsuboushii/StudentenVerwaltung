package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class StudentListViewController extends GenericUIController {
    @FXML private ComboBox<String> cbSort;
    @FXML private ComboBox<String> cbFilter;
    @FXML private ListView<Student> lvElements;
    @FXML private StudentFormFieldsController studentFormFieldsController;

    public void populate() throws SQLException {
        List<Student> students = new DatabaseCrawler().selectAllStudents(Main.getDb().getConn());
        lvElements.getItems().clear();
        lvElements.getItems().addAll(students);
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        lvElements.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Student>) c -> onSelectionChanged()
        );
        studentFormFieldsController.setVisible(false);
        try {
            populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }

    }

    private void onSelectionChanged() {
        Student student = lvElements.getSelectionModel().getSelectedItem();
        if(student == null) {
            try {
                studentFormFieldsController.setVisible(false);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
            }
            return;
        }
        studentFormFieldsController.setStudent(student);
        try {
            studentFormFieldsController.populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }
        try {
            studentFormFieldsController.setVisible(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }
    }
}
