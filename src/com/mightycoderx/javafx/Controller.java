package com.mightycoderx.javafx;

import com.sun.istack.internal.Nullable;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.*;
import javafx.scene.web.WebView;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    public ToolBar toolbar;
    
    @FXML
    private WebView editor;

    @FXML
    public Button btnCompile;

    @FXML
    private Button btnRun;

    @FXML
    public Button btnCompileAndRun;

    @FXML
    private TextArea txtConsole;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        File file = new File("Main.java");

        editor.getEngine().setJavaScriptEnabled(true);
        editor.getEngine().load(Controller.class.getResource("editor.html").toExternalForm().replace("file:/", "file:///"));

        editor.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED)
            {
                editor.getEngine().executeScript("initEditor()");
                toolbar.setDisable(false);
            }
        });

        final KeyCombination copyCombo = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);

        editor.addEventFilter(KeyEvent.KEY_PRESSED, e ->
        {
            if (copyCombo.match(e)) {
                onCopy();
            }
        });

        btnCompile.setOnAction(e ->
        {
            compile(file);
            btnRun.setDisable(false);
        });

        btnRun.setOnAction(e -> run(file));

        btnCompileAndRun.setOnAction(e ->
        {
            compile(file);
            run(file);
        });
    }

    private void onCopy()
    {
        String contentText = (String) editor.getEngine().executeScript("copySelection()");

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(contentText);
        clipboard.setContent(content);
    
        System.out.println("Clipboard: " + clipboard.getString());
    }

    public void compile(File file)
    {
        try
        {
            file.createNewFile();
            FileWriter writer = new FileWriter(file.getPath());
            writer.write((String) editor.getEngine().executeScript("getValue()"));
            writer.close();

            Process pr = Runtime.getRuntime().exec("javac " + file.getPath());
            int exitCode = printProcessOutput(pr);
            if(exitCode == 0)
            {
                println("Compiled successfully!\n");
            }
        }
        catch (IOException | InterruptedException ioException)
        {
            ioException.printStackTrace();
        }
    }

    public void run(File file)
    {
        if(!txtConsole.getText().isEmpty())
        {
            txtConsole.clear();
        }

        try
        {
            Process pr =  Runtime.getRuntime().exec("java " + file.getPath().replace(".java", ""));
            int exitCode = printProcessOutput(pr);
            println("Process ended with exit code " + exitCode);
        }
        catch (IOException | InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    public int printProcessOutput(Process pr) throws InterruptedException
    {
        try
        {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getInputStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null)
            {
                println(s);
            }

            s = null;
            while ((s = stdError.readLine()) != null)
            {
                println(s);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        return pr.waitFor();
    }

    public void println(@Nullable String str)
    {
        if(str == null)
        {
            str = "";
        }
        txtConsole.setText(txtConsole.getText() + str + "\n");
    }
}
