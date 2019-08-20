package sample.dialog.editData;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.datamodel.ToDoItem;

public class EditData {
    @FXML
    private TextField title;

    @FXML
    private DatePicker date;
    @FXML
    private ToDoItem toDoItem;

    public void setToDoItem(ToDoItem toDoItem) {
        this.toDoItem=toDoItem;
        this.title.setText(toDoItem.getTitle());
        date.setValue(toDoItem.getDate());

    }
    public void saveData(){
        toDoItem.setTitle(title.getText());
        toDoItem.setDate(date.getValue());
    }



}
