package com.mightycoderx.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaEditorMain extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));
        window.setTitle("Java Editor");
        window.setScene(new Scene(root, 500, 350));
        window.show();
    }
}
