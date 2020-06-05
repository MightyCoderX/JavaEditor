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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class Controller implements Initializable
{
    @FXML
    public ToolBar toolbar;

    @FXML
    public Button btnCompile;

    @FXML
    private Button btnRun;

    @FXML
    public Button btnCompileAndRun;
    
    @FXML
    private WebView editor;
    
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
        final KeyCombination cutCombo = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
    
    
        editor.addEventFilter(KeyEvent.KEY_PRESSED, e ->
        {
            if (copyCombo.match(e))
            {
                onCopy();
            }
    
            if (cutCombo.match(e))
            {
                onCopy();
            }
        });

        btnCompile.setOnAction(e ->
        {
            txtConsole.clear();
            btnRun.setDisable(false);
            compile(file);
        });

        btnRun.setOnAction(e ->
        {
            txtConsole.clear();
            run(file);
        });

        btnCompileAndRun.setOnAction(e ->
        {
            txtConsole.clear();
    
            if(compile(file))
            {
                run(file);
            }
        });
    }

    private void onCopy()
    {
        String contentText = (String) editor.getEngine().executeScript("copySelection()");

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(contentText);
        clipboard.setContent(content);
    }

    public boolean compile(File file)
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final String value = (String) editor.getEngine().executeScript("getValue()");
        Callable<Boolean> callable = () ->
        {
            try
            {
                file.createNewFile();
                FileWriter writer = new FileWriter(file.getPath());
                try
                {
                    writer.write(value);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                writer.close();
        
                Process pr = Runtime.getRuntime().exec("javac " + file.getPath());
        
                int exitCode = 0;
                try
                {
                    exitCode = printProcessOutput(pr);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (exitCode == 0)
                {
                    println("Compiled successfully!\n");
                    return true;
                }
            }
            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
            return false;
        };
        Future<Boolean> future = executor.submit(callable);
        executor.shutdown();
        try
        {
            return future.get();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
    }

    public void run(File file)
    {
        Path path = Paths.get(file.getAbsolutePath());
        ProcessBuilder pb = new ProcessBuilder("cmd", "/k", "start", "cmd", "/c", "\"echo off & cd " +
                path.getParent().toString() + " & java " + path.getFileName().toString().replace(".java", "") + " & pause\"");
        try
        {
            Process p = pb.start();
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        
        /*new Thread(() ->
        {
            try
            {
                Process pr = Runtime.getRuntime().exec("java " + file.getPath().replace(".java", ""));
                Platform.runLater(() ->
                {
                    try
                    {
                        println("Process ended with exit code " + printProcessOutput(pr));
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                });
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }).run();*/
    }

    public int printProcessOutput(Process pr) throws InterruptedException
    {
        try
        {
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

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
