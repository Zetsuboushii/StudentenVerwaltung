package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import de.dbsys.app.ui.utils.UILoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;


public class MainController extends GenericUIController {
    @FXML private StudentListViewController studentListViewController;
    @FXML private CourseListViewController courseListViewController;

    /**
     * Reloads the list views with all students and courses from the database.
     */
    public void reload() {
        studentListViewController.reload();
        courseListViewController.reload();
    }

    /**
     * Register keyboard shortcuts (for creating new students + courses).
     */
    private void registerKeyboardShortcuts() {
        registerNewStudentKeyboardShortcut();
        registerNewCourseKeyboardShortcut();
    }

    /**
     * Register keyboard shortcuts for creating a new student.
     */
    private void registerNewStudentKeyboardShortcut() {
        KeyCodeCombination newStudentMac = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);
        KeyCodeCombination newStudentWin = new KeyCodeCombination(KeyCode.L, KeyCombination.ALT_DOWN);
        // Button just temporary until the shortcut can be sent to the existing button
        Button btn = new Button("");
        btn.setOnAction(e -> neuerStudierender());
        stage.getScene().addMnemonic(new Mnemonic(btn, newStudentWin));
        stage.getScene().addMnemonic(new Mnemonic(btn, newStudentMac));
    }

    /**
     * Register keyboard shortcuts for creating a new course.
     */
    private void registerNewCourseKeyboardShortcut() {
        KeyCodeCombination newCourseMac = new KeyCodeCombination(KeyCode.K, KeyCombination.SHORTCUT_DOWN);
        KeyCodeCombination newCourseWin = new KeyCodeCombination(KeyCode.K, KeyCombination.ALT_DOWN);
        // Button just temporary until the shortcut can be sent to the existing button
        Button newCourseBtn = new Button("");
        newCourseBtn.setOnAction(e -> neuerKurs());
        stage.getScene().addMnemonic(new Mnemonic(newCourseBtn, newCourseWin));
        stage.getScene().addMnemonic(new Mnemonic(newCourseBtn, newCourseMac));
    }

    @FXML
    private void onTabChanged() {
        if(studentListViewController == null || courseListViewController == null) {
            return;
        }
        studentListViewController.reload();
        courseListViewController.reload();
    }

    /**
     * Show new window for creating a new student.
     */
    @FXML
    public void neuerStudierender() {
        try {
            UILoader.showFXML("new-student-view", "Neue:r Studierende:r");
        } catch (Exception exc) {
            handleException(exc);
        }
    }

    /**
     * Show new window creating a new course.
     */
    @FXML
    public void neuerKurs() {
        try {
            UILoader.showFXML("new-course-view", "Neuer Kurs");
        } catch (Exception exc) {
            handleException(exc);
        }
    }

    @Override
    public void onBeforeShow(Stage stage) {
        registerKeyboardShortcuts();
    }

    @Override
    public void onAfterShow(Stage stage) throws Exception {
        super.onAfterShow(stage);
        studentListViewController.onAfterShow(stage);
        courseListViewController.onAfterShow(stage);
    }

    @Override
    protected void setRootVisible(boolean visible) {
        throw new UnsupportedOperationException("Not supported on controllers that are directly loaded onto scenes.");
    }
}