package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseCrawler;
import de.dbsys.app.database.entities.Course;
import de.dbsys.app.database.entities.Student;
import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.comparators.FirstNameStudentComparator;
import de.dbsys.app.ui.utils.comparators.LastNameStudentComparator;
import de.dbsys.app.ui.utils.filters.CourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoCourseStudentFilter;
import de.dbsys.app.ui.utils.filters.NoneStudentFilter;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StudentListViewController extends GenericUIController {
    @FXML private ComboBox<Comparator<Student>> cbSort;
    @FXML private ComboBox<Predicate<Student>> cbFilter;
    @FXML private ListView<Student> lvElements;
    @FXML private EditStudentViewController editStudentViewController;

    private List<Student> students;

    private final List<Comparator<Student>> comparators = List.of(
            new FirstNameStudentComparator(),
            new LastNameStudentComparator()
    );

    public void reload() {
        try {
            populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }
    }


    public void populate() throws SQLException {
        students = new DatabaseCrawler().selectAllStudents(Main.getDb().getConn());

        try {
            editStudentViewController.setVisible(false);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim aktualisiren der Elemente.\n" + e.getMessage()).show();
        }

        cbSort.getItems().clear();
        cbSort.getItems().addAll(comparators);
        cbSort.getSelectionModel().select(0);

        cbFilter.getItems().clear();
        cbFilter.getItems().add(new NoneStudentFilter());
        cbFilter.getItems().add(new NoCourseStudentFilter());
        List<Course> courses = new DatabaseCrawler().selectAllCourses(Main.getDb().getConn());
        cbFilter.getItems().addAll(courses.stream().map(CourseStudentFilter::new).toList());
        cbFilter.getSelectionModel().select(0);

        lvElements.getItems().clear();
        lvElements.getItems().addAll(students.stream().filter(cbFilter.getSelectionModel().getSelectedItem()).toList());
        lvElements.getItems().sort(cbSort.getSelectionModel().getSelectedItem());
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        lvElements.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Student>) c -> onSelectionChanged()
        );
        cbSort.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null)
                        lvElements.getItems().sort(newValue);
                }
        );
        cbFilter.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue != null && cbSort.getSelectionModel().getSelectedItem() != null) {
                        lvElements.getItems().clear();
                        lvElements.getItems().addAll(students.stream().filter(newValue).toList());
                        lvElements.getItems().sort(cbSort.getSelectionModel().getSelectedItem());
                    }
                }
        );
        editStudentViewController.setVisible(false);
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
                editStudentViewController.setVisible(false);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
            }
            return;
        }
        editStudentViewController.setStudent(student);
        try {
            editStudentViewController.populate();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }
        try {
            editStudentViewController.setVisible(true);
            editStudentViewController.populate();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Fehler beim Laden der Studenten.\n" + e.getMessage()).show();
        }
    }
}
