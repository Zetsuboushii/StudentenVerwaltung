package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
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
