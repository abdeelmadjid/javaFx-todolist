package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import sample.datamodel.ToDoDataHandeler;
import sample.datamodel.ToDoItem;
import sample.dialog.addData.AddNewDialogController;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class MainUiController {

    @FXML
    private ToggleButton todayAllToggle;
    @FXML
    private ListView<ToDoItem> toDoListView;
    @FXML
    private TextArea todoTextArea;
    @FXML
    private Label dateLabel;
    @FXML
    private BorderPane mainUi;
    @FXML
    private ToDoItem selectedItem;
    @FXML
    private Menu menu;
    @FXML
    private ContextMenu contextMenu;
    private SortedList<ToDoItem> sortedList;
    private FilteredList<ToDoItem> filteredList;

    //task=data=todoData=item this is just a hint so u dont get lost in documentation

    @FXML
    public void initialize() throws FileNotFoundException {

        //setting up the info menuItem dialog
        MenuItem info=new MenuItem("info");
        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initOwner(mainUi.getScene().getWindow());

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("dialog/info/infoDialog.fxml"));
                try {
                    dialog.getDialogPane().setContent(fxmlLoader.load());

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return;
                }
                dialog.setTitle("todolist info");
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.showAndWait();




            }
        });
        //adding the info menuItem to the menu
        menu.getItems().addAll(info);

        // get the data from ToDoHandler class and sorting it
        // by the date
         sortedList=new SortedList<ToDoItem>(ToDoDataHandeler.getInstance().getItems(), new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                if (o1.getDate().isBefore(o2.getDate()))return 1;
               else if (o1.getDate().isAfter(o2.getDate()))return -1;
              else return 0;
            }
        });
         //adding the todoData to the list view
        toDoListView.setItems(sortedList);
        //make the list view selection mode to single (user cant select multiple items in list view)
        toDoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //add selection listener for the list view to update UI data (todoTextArea-date label
        toDoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ToDoItem>() {
            @Override
            public void changed(ObservableValue<? extends ToDoItem> observable, ToDoItem oldValue, ToDoItem newValue) {
                if (newValue!=null){
                    ToDoItem item=(ToDoItem) toDoListView.getSelectionModel().getSelectedItem();
                    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("MMMM d, yyyy");
                    dateLabel.setText(dtf.format(item.getDate()));
                    todoTextArea.setText(item.getTodo());
                    selectedItem=item;
                }
            }
        });
        // when lunch program the top item in listview will be selcted
        toDoListView.getSelectionModel().selectFirst();
        //setting up the context menu when right click on item in the listveiw a popup menu will come up that have menuItem of  deleting item selected
        contextMenu=new ContextMenu();
        MenuItem delteCntxMenu=new MenuItem("delete this item");
        delteCntxMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteItemAlert();
            }
        });
        //add the menuItem to the context menu
        contextMenu.getItems().add(delteCntxMenu);





        //make a custom cells for the list view depending on the item
        toDoListView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {


            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> Cell=new ListCell<ToDoItem>(){



                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        //okey someTimes do the super thing in Overrided  methods cuz some times u forget to add a lot of missing code that can lead to a lot of bugs or u can easily go to mthod an see what nedded
                        //for me i like to add it in first then add my code
                        super.updateItem(item,empty);


                        //getting and initialzing the date and warrning icon
                        Image image=new Image("sample/icons/warning.png");
                        LocalDate now = LocalDate.now();
                        //setting the date of formatt (2019-12-31) like the date we have in our data
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


                        //allways set these to null because when the listview is changed or updated it will stay in the way it was before example(a cell with red color and u delte the item in that cell it will stay red)
                        setText(null);
                        setGraphic(null);
                        setStyle(null);

                        if(!empty){

                            setText(item.getTitle());
                            LocalDate itemDate=LocalDate.parse(item.getDate().format(dtf));
                            if (itemDate.equals(now)){

                              //  setStyle("-fx-background-color:#00c853;");
                                setGraphic(new ImageView(image));
                               // setTextFill(Color.RED);


                            }else if(itemDate.isAfter(now)&& itemDate.isBefore(now.plusDays(2))){
                                setStyle("-fx-background-color:#00c853;");
                            }



                        }




                    }
                };
                // add a cell listner when its empty or have item  for our contextMenu so there will be no context menu for empty cells
                Cell.emptyProperty().addListener(
                        (obs,wasEmpty,isNowEmpty)->{
                            if (isNowEmpty) {
                                Cell.setContextMenu(null);
                            } else {
                                Cell.setContextMenu(contextMenu);
                            }
                        }
                );
                return Cell;
            }
        });










    }

    //setting up the dialodg for adding new data
    @FXML
    private void addNewData(){
        //make sure that our listview is showing all the data not filtered one so we can select the item that just had ben added
        if (!todayAllToggle.isSelected())todayOrAll();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainUi.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("dialog/addData/addNewDialog.fxml"));


            try {
                dialog.getDialogPane().setContent(fxmlLoader.load());
                dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                dialog.setTitle("Add new Data");


                //a looop that make the Add dilog keep shoing when useer atempt to save new data whith empty (date....todoData...tiltle)
                while (true){
                    AddNewDialogController controller = fxmlLoader.getController();
                    Optional<ButtonType> resault = dialog.showAndWait();



                    if (resault.isPresent() && resault.get()==ButtonType.OK){
                        //we test if all the data is valid so we can add our item
                        if (controller.dataIsEntered()){
                            //we add the item and get the title item returned so we can selct that item added
                            String itemTitleAdded=controller.okButtonListner();
                            for (ToDoItem item:sortedList){
                                item.getTitle().equals(itemTitleAdded);
                                toDoListView.getSelectionModel().select(item);

                            }
                            break;
                        }



                    }
                    if (resault.isPresent() && resault.get()==ButtonType.CANCEL)break;
                    //if our loop didnt break that mean user has forget to add title or date or todoText so we give him a msg in dilog header
                    controller.setError();

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }










    }

    //when ever the user type somthing in text area we save that
    @FXML
    private void updateTextArea(){
        selectedItem.setTodo(todoTextArea.getText());
    }

    // delete confirmation alert
    private void deleteItemAlert(){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);


        alert.setTitle("delte confirmation");
        alert.setHeaderText("delte "+selectedItem.getTitle());
        alert.setContentText("are you sure u want to delte "+selectedItem.getTitle());




        Optional<ButtonType> reasult=alert.showAndWait();
        if (reasult.isPresent() && (reasult.get()==ButtonType.OK)){
           ToDoDataHandeler.getInstance().deleteItem(selectedItem);

        }

    }

    //delete item by keyboard
    @FXML
    private void deleteSelected(KeyEvent keyEvent){

        if (selectedItem.getTitle().isEmpty())return;
        else if (keyEvent.getCode().equals(KeyCode.DELETE)){
            deleteItemAlert();
        }


    }



    //toggle button listner to filter data by today tasks or all
    @FXML
    private void todayOrAll(){
        if (todayAllToggle.isSelected()) {
            filteredList=new FilteredList<ToDoItem>(sortedList, new Predicate<ToDoItem>() {
                @Override
                public boolean test(ToDoItem toDoItem) {
                    if (toDoItem.getDate().equals(LocalDate.now())) return true;
                    return false;
                }
            });
            toDoListView.setItems(filteredList);
            todayAllToggle.setText("All");

        }else{
            toDoListView.setItems(sortedList);
            todayAllToggle.setText("today");

        }



    }

   



}

