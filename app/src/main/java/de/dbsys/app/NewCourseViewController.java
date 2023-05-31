package de.dbsys.app;

import javafx.fxml.FXML;

public class NewCourseViewController extends GenericUIController {
    @FXML
    private void onCancel() {
        stage.close();
    }

    @FXML
    private void onSave() {
        stage.close();
    }
}
