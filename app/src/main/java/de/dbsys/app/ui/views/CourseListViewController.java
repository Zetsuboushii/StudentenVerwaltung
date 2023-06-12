package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.comparators.NameCourseComparator;
import de.dbsys.app.ui.utils.comparators.RoomCourseComparator;
import de.dbsys.app.ui.utils.ui.UiStyler;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class CourseListViewController extends GenericUIController {
    @FXML
    private ComboBox<Comparator<Course>> cbSort;
    @FXML private ListView<Course> lvElements;
    @FXML private EditCourseViewController editCourseViewController;

    private final List<Comparator<Course>> comparators = List.of(
            new NameCourseComparator(),
            new RoomCourseComparator()
    );

    public void reload() {
        try {
            populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
        }
    }

    public void populate() throws SQLException {
        cbSort.getItems().clear();
        cbSort.getItems().addAll(comparators);
        cbSort.getSelectionModel().select(0);
        UiStyler.makeSortBox(cbSort);
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
        cbSort.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null) {
                        lvElements.getItems().sort(newValue);
                    }
                }
        );
        editCourseViewController.setVisible(false);
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
                editCourseViewController.setVisible(false);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
            }
            return;
        }
        editCourseViewController.setCourse(course);
        try {
            editCourseViewController.setVisible(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Kurse.\n" + e.getMessage()).show();
        }
    }
}
