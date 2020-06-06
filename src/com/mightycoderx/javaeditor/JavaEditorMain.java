package com.mightycoderx.javaeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaEditorMain extends Application
{
    public static Stage window;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception
    {
        this.window = window;
        Parent rootSceneLayout = FXMLLoader.load(getClass().getResource("RootScene.fxml"));
        window.setTitle("Java Editor");
        
        Scene rootScene = new Scene(rootSceneLayout, 500, 350);
        
        window.setScene(rootScene);
        window.getScene().getStylesheets().add("stylesheets/Scene.css");
        window.show();
    }
}
