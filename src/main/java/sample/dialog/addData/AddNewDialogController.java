package sample.dialog.addData;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.datamodel.ToDoDataHandeler;
import sample.datamodel.ToDoItem;

import java.time.LocalDate;


public class AddNewDialogController {


    @FXML
    private Label errorLabel;
    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea todoTextArea;


    //add new item
    public String okButtonListner(){
        String title=titleTextField.getText();
        LocalDate localDate=datePicker.getValue();
        String todo=todoTextArea.getText();
        int trying=0;

        //loop tht make sure we dont have duplicated title exmpl(if we alredy have an item with the title birthday and user want to add another one with same title it will aded automaticli as birthday (1) )
        while(ToDoDataHandeler.getInstance().addNewData(new ToDoItem(title,localDate,todo))){
            title=title.replace(" ("+trying+")","");
            trying++;
            title+=" ("+trying+")";


        }
        return title;

    }

    //test if data is valid
    public boolean dataIsEntered(){
        if(titleTextField.getText().isEmpty() || todoTextArea.getText().isEmpty() || datePicker.getValue()==null){
         return false;
        }
        return true;
    }

    //set header to "ERROR:please enter all data" when try to save with non valid data
    public void setError() {
       errorLabel.setVisible(true);

    }
}
