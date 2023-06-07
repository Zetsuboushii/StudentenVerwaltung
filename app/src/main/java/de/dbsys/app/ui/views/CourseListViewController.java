package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
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
    public void onAfterShow(Stage stage) {
        super.onAfterShow(stage);
        try {
            populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
        }

    }
}