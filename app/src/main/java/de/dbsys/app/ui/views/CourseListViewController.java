package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.ui.Main;
import de.dbsys.app.ui.utils.comparators.DHBWRoomCourseComparator;
import de.dbsys.app.ui.utils.comparators.NameCourseComparator;
import de.dbsys.app.ui.utils.comparators.RoomCourseComparator;
import de.dbsys.app.ui.utils.ui.CourseListCellFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class CourseListViewController extends ListViewController {
    @FXML private SplitPane root;
    @FXML
    private ComboBox<Comparator<Course>> cbSort;
    private List<Course> courses;
    @FXML private ListView<Course> lvElements;
    @FXML private EditCourseViewController editCourseViewController;
    @FXML private ListSearchViewController<Course> listSearchViewController;

    private final List<Comparator<Course>> comparators = List.of(
            new NameCourseComparator(),
            new RoomCourseComparator(),
            new DHBWRoomCourseComparator()
    );


    @Override
    public void populate() throws SQLException {
        populateSortBox();

        populateList();
    }

    /**
     * Populates the sort box with all comparators.
     */
    private void populateSortBox() {
        makeSortBox(cbSort);
        cbSort.getItems().clear();
        cbSort.getItems().addAll(comparators);
        cbSort.getSelectionModel().select(0);
    }

    /**
     * Populates the list with all courses from the database.
     * @throws SQLException if an error occurs while querying the database
     */
    private void populateList() throws SQLException {
        this.courses = new DatabaseCrawler().selectAllCourses(Main.getDb().getConn());
        lvElements.setCellFactory(new CourseListCellFactory());
        listSearchViewController.setSourceItems(courses);
        listSearchViewController.filterItems();
        try {
            editCourseViewController.setVisible(false);
        } catch (Exception e) {
            handleException(e, "Fehler beim Aktualisieren der Liste: ");
        }
    }

    @Override
    public void onBeforeShow(Stage stage) throws Exception {
        lvElements.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Course>) c -> onSelectionChanged()
        );
        cbSort.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    listSearchViewController.setComparator(newValue);
                    listSearchViewController.filterItems();
                }
        );
        editCourseViewController.setVisible(false);
        super.onBeforeShow(stage);

        listSearchViewController.initialize(courses, cbSort.getSelectionModel().getSelectedItem());
        lvElements.setItems(listSearchViewController.getFilteredItems());
    }

    /**
     * Sets the selected course in the edit course view and shows or hides it appropriately.
     */
    private void onSelectionChanged() {
        Course course = lvElements.getSelectionModel().getSelectedItem();
        if(course == null) {
            try {
                editCourseViewController.setVisible(false);
            } catch (Exception e) {
                handleException(e, "Fehler beim Laden der Kurse: ");
            }
            return;
        }
        editCourseViewController.setCourse(course);
        try {
            editCourseViewController.populate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            editCourseViewController.setVisible(true);
        } catch (Exception e) {
            handleException(e, "Fehler beim Laden der Kurse: ");
        }
    }

    @Override
    protected void setRootVisible(boolean visible) {
        root.setVisible(visible);
    }
}
