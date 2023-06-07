package de.dbsys.app.ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class GenericUIController implements  UIController{
    protected Stage stage;

    @Override
    public void onBeforeShow(Stage stage) {}

    @Override
    public void onAfterShow(Stage stage) {}

    public void setVisible(boolean visible) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    protected void handleException(Exception exc) {
        exc.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Ein Fehler ist aufgetreten: " + exc).show();
    }
}
