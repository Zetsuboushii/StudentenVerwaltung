package de.dbsys.app.ui.views;

import de.dbsys.app.database.DatabaseConnector;
import de.dbsys.app.ui.utils.UILoader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    public static DatabaseConnector db;

    @Override
    public void start(Stage stage) throws Exception {
        UILoader.showFXML("main", "Studierenden-Verwaltung");
    }

    public static DatabaseConnector getDb() {
        return db;
    }

    public static void main(String[] args) throws SQLException {
        db = new DatabaseConnector();
        //DBTester.main(args);
        launch();
    }
}