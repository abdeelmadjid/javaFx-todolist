package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.ToDoDataHandeler;

public class Main extends Application {


    @Override
    public void init() throws Exception {

        //load data from txt file if exist
        try{
            ToDoDataHandeler.getInstance().loadToDoItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }    }

    @Override
    public void stop() throws Exception {
        //save data when exit
       try{
            ToDoDataHandeler.getInstance().saveTodoItems();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainUi.fxml"));
        primaryStage.setTitle("FXTodoList");
        Scene scene=new Scene(root,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
