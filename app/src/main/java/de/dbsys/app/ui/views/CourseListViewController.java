package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class CourseListViewController extends GenericUIController {
    @FXML
    private ComboBox<String> cbSort;
    @FXML private ComboBox<String> cbFilter;
    @FXML private ListView<Course> lvElements;
    @FXML private CourseFormFieldsController courseFormFieldsController;

    public void populate() throws SQLException {
        List<Course> courses = new DatabaseCrawler().selectAllCourses(Main.getDb().getConn());
        lvElements.getItems().clear();
        lvElements.getItems().addAll(courses);
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        lvElements.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Course>) c -> onSelectionChanged()
        );
        courseFormFieldsController.setVisible(false);
        try {
            populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
        }

    }

    private void onSelectionChanged() {
        Course course = lvElements.getSelectionModel().getSelectedItem();
        if(course == null) {
            try {
                courseFormFieldsController.setVisible(false);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
            }
            return;
        }
        courseFormFieldsController.setCourse(course);
        try {
            courseFormFieldsController.setVisible(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
        }
    }
}
