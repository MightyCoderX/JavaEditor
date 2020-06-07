package com.mightycoderx.javaeditor.controllers;

import com.mightycoderx.javaeditor.JavaEditorMain;
import com.mightycoderx.javaeditor.dialogs.NewProjectDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RootScene implements Initializable
{
    public static RootScene instance = new RootScene();
    
    @FXML
    public VBox layout;
    
    //File Menu
    @FXML
    public MenuItem menuItemNew;
    
    @FXML
    public MenuItem menuItemOpen;
    
    @FXML
    public MenuItem menuItemSave;
    
    //Edit Menu
    @FXML
    public MenuItem menuItemUndo;
    
    @FXML
    public MenuItem menuItemRedo;
    
    @FXML
    public MenuItem menuItemCopy;
    
    @FXML
    public MenuItem menuItemCut;
    
    @FXML
    public MenuItem menuItemPaste;
    
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
        
        //File Menu
        menuItemNew.setOnAction(e ->
        {
            NewProjectDialog newProjectDialog = new NewProjectDialog();
            
            
        });
    
        menuItemOpen.setOnAction(e ->
        {
        
        });
        
        menuItemSave.setOnAction(e ->
        {
        
        });
        
        //Edit Menu
        menuItemUndo.setOnAction(e -> EditScreen.instance.undo());
        
        menuItemRedo.setOnAction(e -> EditScreen.instance.redo());
        
        menuItemCopy.setOnAction(e -> EditScreen.instance.copy());
    
        menuItemCut.setOnAction(e -> EditScreen.instance.cut());
        
        menuItemPaste.setOnAction(e -> EditScreen.instance.paste());
        
    }
}
