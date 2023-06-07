package de.dbsys.app.ui.utils;

import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UILoader {
    public static FXMLLoader getFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        URL xmlURL = UILoader.class.getResource("/de/dbsys/app/" + fxmlPath + ".fxml");
        loader.setLocation(xmlURL);
        return loader;
    }

    public static <T extends GenericUIController> T loadFXMLOnStage(String fxmlPath, Stage stage, String title) throws Exception {
        FXMLLoader loader = getFXMLLoader(fxmlPath);
        Parent root = loader.load();

        stage.setTitle(title);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return loader.getController();
    }

    public static <T extends GenericUIController> T showFXMLOnStage(String fxmlPath, Stage stage, String title) throws Exception {
        T controller = loadFXMLOnStage(fxmlPath, stage, title);
        controller.setStage(stage);
        controller.onBeforeShow(stage);
        stage.show();
        controller.onAfterShow(stage);
        return controller;
    }

    public static <T extends GenericUIController> T showFXML(String fxmlPath, String title) throws Exception {
        return showFXMLOnStage(fxmlPath, new Stage(), title);
    }
}
