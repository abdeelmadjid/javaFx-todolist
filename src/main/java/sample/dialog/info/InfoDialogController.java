package sample.dialog.info;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;


public class InfoDialogController implements Initializable {
    @FXML
    private Label instagram;
    @FXML
    private Label gmail;
    @FXML
    private DialogPane deleteDilogPane;
    private Desktop desktop;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        desktop = Desktop.getDesktop();
    }

    //open my instgram when click on it
    public void openGram() throws Exception{
        String url = "https://www.instagram.com/abdeel_madjid/";
        URI uri = URI.create(url);
        desktop.browse(uri);

    }

    //open mail to my gmail
    public void openGmail() throws Exception{
        String url = "mailto:john@example.com?subject=Hello%20World";
        URI uri = URI.create(url);
        desktop.mail(uri);

    }
    public void openGithub() throws Exception{
        String url = "https://github.com/abdeelmadjid/javaFx-todolist";
        URI uri = URI.create(url);
        desktop.browse(uri);

    }




}
