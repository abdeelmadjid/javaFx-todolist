package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.Settings;
import sample.datamodel.ToDoDataHandeler;

public class Main extends Application {
    private MainUiController mainUiController;


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
            Settings.getInstance().setDark(mainUiController.isDark());
            Settings.getInstance().setSplit(mainUiController.getSplitD());
           Settings.getInstance().saveData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(getClass().getResource("mainUi.fxml"));
        Parent root=fxmlLoader.load();
        primaryStage.setTitle("FXTodoList");
        Scene scene=new Scene(root,600,400);
         mainUiController=fxmlLoader.getController();
        mainUiController.updateThemeData(Settings.getInstance().isDark(),Settings.getInstance().getSplit());
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
