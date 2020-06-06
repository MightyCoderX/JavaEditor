package com.mightycoderx.javaeditor.controllers;

import com.mightycoderx.javaeditor.JavaEditorMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootScene implements Initializable
{
    @FXML
    public VBox layout;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            JavaEditorMain.window.setTitle(JavaEditorMain.window.getTitle()+" - Start Screen");
            layout.getChildren().add(FXMLLoader.load(getClass().getResource("../StartScreen.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
