package com.mightycoderx.javaeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaEditorMain extends Application
{
    Stage window;
    
    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception
    {
        this.window = window;
        Parent rootSceneLayout = FXMLLoader.load(getClass().getResource("RootScene.fxml"));
        Parent startScreenLayout = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Parent editScreenLayout = FXMLLoader.load(getClass().getResource("EditScreen.fxml"));
        window.setTitle("Java Editor - Start Screen");
        
        rootSceneLayout.getChildrenUnmodifiable().add(startScreenLayout);
        Scene rootScene = new Scene(rootSceneLayout, 500, 350);
        
        window.setScene(rootScene);
        window.getScene().getStylesheets().add("Scene.css");
        window.show();
    }
}
