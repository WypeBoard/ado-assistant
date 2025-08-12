package io.github.wypeboard.adoassistant;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import io.github.wypeboard.adoassistant.view.DashboardView;

public class AdoAssistantApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("ADO Assistant");

        // Create views
        DashboardView dashboardView = new DashboardView();

        Scene scene = new Scene(dashboardView.getRoot(), 1024, 768);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}