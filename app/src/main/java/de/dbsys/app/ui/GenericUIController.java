package de.dbsys.app.ui;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class GenericUIController implements  UIController{
    protected Stage stage;

    @Override
    public void onBeforeShow(Stage stage) throws Exception {}

    @Override
    public void onAfterShow(Stage stage) throws Exception {}

    public void setVisible(boolean visible) throws Exception {
        onBeforeShow(null);
        setRootVisible(visible);
        onAfterShow(null);
    }

    public void setRootVisible(boolean visible) {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    protected void handleException(Exception exc, String message) {
        exc.printStackTrace();
        new Alert(Alert.AlertType.ERROR, message + exc).show();
    }

    protected void handleException(Exception exc) {
        handleException(exc, "Ein Fehler ist aufgetreten: ");
    }
}
