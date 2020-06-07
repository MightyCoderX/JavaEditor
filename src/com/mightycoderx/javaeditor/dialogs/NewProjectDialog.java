package com.mightycoderx.javaeditor.dialogs;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewProjectDialog extends Stage
{
    public NewProjectDialog()
    {
        TextField txtDirectory = new TextField();
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(txtDirectory);
        
        Scene scene = new Scene(layout, 500, 500);
    }
}
