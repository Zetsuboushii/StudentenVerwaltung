package de.dbsys.app;

import de.dbsys.app.utils.UILoader;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UILoader.showFXML("main", "Studierenden-Verwaltung");
    }


    public static void main(String[] args) {
        launch();
    }
}