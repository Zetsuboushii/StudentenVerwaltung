package de.dbsys.app.ui.views;

import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class NewStudentViewController extends GenericUIController {
    @FXML
    private void onCancel() {
        stage.close();
    }

    @FXML
    private void onSave() {
        stage.close();
    }

    @Override
    public void onAfterShow(Stage stage) {
        stage.minWidthProperty().setValue(400);
    }
}
