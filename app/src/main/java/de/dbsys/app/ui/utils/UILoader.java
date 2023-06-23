package de.dbsys.app.ui.utils;

import de.dbsys.app.ui.GenericUIController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class UILoader {
    /**
     * Gets the FXMLLoader for the given fxml filer.
     * @param fxmlPath The path to the fxml file from the resources directory without the file extension.
     * @return The FXMLLoader.
     */
    public static FXMLLoader getFXMLLoader(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        URL xmlURL = UILoader.class.getResource("/de/dbsys/app/" + fxmlPath + ".fxml");
        loader.setLocation(xmlURL);
        return loader;
    }

    /**
     * Loads the specified fxml file on the specified stage and returns the controller.
     * @param fxmlPath The path to the fxml file from the resources directory without the file extension.
     * @param stage The stage to load the fxml file on.
     * @param title The title of the stage.
     * @return The controller of the fxml file.
     * @param <T> The type of the controller.
     * @throws Exception If any error occurs while loading the fxml file.
     */
    public static <T extends GenericUIController> T loadFXMLOnStage(String fxmlPath, Stage stage, String title) throws Exception {
        FXMLLoader loader = getFXMLLoader(fxmlPath);
        Parent root = loader.load();

        stage.setTitle(title);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        return loader.getController();
    }

    /**
     * Loads the specified fxml file on the specified stage, shows the stage and returns the controller.
     * @param fxmlPath The path to the fxml file from the resources directory without the file extension.
     * @param stage The stage to load the fxml file on.
     * @param title The title of the stage.
     * @return The controller of the fxml file.
     * @param <T> The type of the controller.
     * @throws Exception If any error occurs while loading the fxml file.
     */
    public static <T extends GenericUIController> T showFXMLOnStage(String fxmlPath, Stage stage, String title) throws Exception {
        T controller = loadFXMLOnStage(fxmlPath, stage, title);
        controller.setStage(stage);
        controller.setVisible(true, stage);
        stage.show();
        return controller;
    }

    /**
     * Shows the specified fxml file on a new stage and returns the controller.
     * @param fxmlPath The path to the fxml file from the resources directory without the file extension.
     * @param title The title of the stage.
     * @return The controller of the fxml file.
     * @param <T> The type of the controller.
     * @throws Exception If any error occurs while loading the fxml file.
     */
    public static <T extends GenericUIController> T showFXML(String fxmlPath, String title) throws Exception {
        return showFXMLOnStage(fxmlPath, new Stage(), title);
    }
}
