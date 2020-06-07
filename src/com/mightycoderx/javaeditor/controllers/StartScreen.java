package com.mightycoderx.javaeditor.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreen implements Initializable
{
    public static StartScreen instance = new StartScreen();
    
    @FXML
    public Hyperlink githubLink;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        githubLink.setOnAction(e ->
        {
            try
            {
                Runtime.getRuntime().exec("cmd /c start \"\" \"https://github.com/MightyCoderX/JavaEditor\"");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        });
    }
}
