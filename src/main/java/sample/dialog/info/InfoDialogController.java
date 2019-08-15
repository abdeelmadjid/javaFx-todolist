package sample.dialog.info;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.net.URI;


public class InfoDialogController  {
    @FXML
    private Label instagram;
    @FXML
    private Label gmail;


    //open my instgram when click on it
    public void openGram() throws Exception{
        Desktop desktop = Desktop.getDesktop();
        String url = "https://www.instagram.com/abdeel_madjid/";
        URI uri = URI.create(url);
        desktop.browse(uri);

    }
    //chenge style of label when hover so he know he can click
    public void focusGram(){
        instagram.setStyle("-fx-background-color: #3D32BC");
        Paint paint=Paint.valueOf("#FFFFFF");
        instagram.setTextFill(paint);
    }
    //open mail to my gmail
    public void openGmail() throws Exception{
        Desktop desktop = Desktop.getDesktop();
        String url = "mailto:john@example.com?subject=Hello%20World";
        URI uri = URI.create(url);
        desktop.mail(uri);

    }
    //chenge style of label when hover so he know he can click
    public void focusGmail(){

        gmail.setStyle("-fx-background-color: #3D32BC");
        Paint paint=Paint.valueOf("#FFFFFF");
        gmail.setTextFill(paint);
    }





    // return label to default style when not hovering so we can focus again when hover
    public void unfocusGram(){

        instagram.setStyle("-fx-background-color: white");
        Paint paint=Paint.valueOf("#3D32BC");
        instagram.setTextFill(paint);
    }

    // return label to default style when not hovering so we can focus again when hover
    public void unfocusGmail(){

        gmail.setStyle("-fx-background-color: white");
        Paint paint=Paint.valueOf("#3D32BC");
        gmail.setTextFill(paint);
    }


}
