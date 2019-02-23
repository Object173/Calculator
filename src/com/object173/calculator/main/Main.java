package com.object173.calculator.main;

import com.object173.calculator.resources.ResourcesManager;
import com.object173.calculator.resources.StringResKey;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private ResourcesManager mResManager;

    @Override
    public void init() throws Exception {
        super.init();
        mResManager = ResourcesManager.getInstance();
    }

    @Override
    public void start(final Stage primaryStage) throws Exception{
        final Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
        primaryStage.setTitle(mResManager.getString(StringResKey.CALCULATOR_WINDOW_TITLE));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.getScene().getStylesheets().add("css/BaseTheme.css");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
