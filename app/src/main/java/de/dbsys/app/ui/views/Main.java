package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.ui.utils.UILoader;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

public class Main extends Application {
    private static DatabaseConnector db;
    private static MainController mainController;

    @Override
    public void start(Stage stage) throws Exception {
        mainController = UILoader.showFXMLOnStage("main", stage, "Studierenden-Verwaltung");
        setIcon(stage);
    }

    private void setIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/de/dbsys/app/app-icon.png"))));
    }

    /**
     * Returns the database connector with an open connection.
     * @return the database connector
     */
    public static DatabaseConnector getDb() {
        return db;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void main(String[] args) throws SQLException {
        db = new DatabaseConnector();
        launch();
    }
}