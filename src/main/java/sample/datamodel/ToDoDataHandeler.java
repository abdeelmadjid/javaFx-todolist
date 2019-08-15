package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class ToDoDataHandeler {
    private static ToDoDataHandeler instance=new ToDoDataHandeler();
    private static  String fileName="dataTxt.txt";
    private ObservableList<ToDoItem> toDoItems;
    private DateTimeFormatter dtf;

    public static ToDoDataHandeler getInstance(){
     return instance;
    }

    private ToDoDataHandeler() {
        dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public ObservableList<ToDoItem> getItems() {
        return toDoItems;
    }


    //load data from our dataTxt.txt file
    /**
     * the data in txt file is like{
     *     molly birthday                                  <<<<the title
     *     2019-12-31                                      <<<<the date
     *     go get cake an birthday gifts                   <<<<todotext
     *     dont forget the fireworks
     *     &:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:      <<<<separator the end of an todo tesk
     *     job enterview                                   <<<<the title
     *     2019-12-31                                      <<<<the date
     *     go to google company 9:00 am                    <<<<todotext
     *     get the best outfit so u rock the enterveiw
     *     &:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:      <<<<separator the end of an todo tesk
     *
     * }
     * so we loop thoth the data to read the data or write it like :
     * first line is title
     * second is date
     * third until the separator "&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:"
     * then the first line after the separator is the next todo title
     * second is date
     * .....
     * .....
     * **/




    //load the data from txt to our observableArrayList
    public void loadToDoItems() throws IOException {
        toDoItems= FXCollections.observableArrayList();
        Path path= Paths.get(fileName);
        BufferedReader bufferedReader= Files.newBufferedReader(path);
        String data;
        try{

            String title="";
            String dateString="";
            String todo="";
            int count=0;
            boolean finish=false;
            while((data=bufferedReader.readLine())!=null){
                switch (count){
                    case 0:
                        title=data;
                        count++;
                        break;
                    case 1:
                        dateString=data;
                        count++;
                        break;
                    case 2:
                        todo=data;
                        while (!(data=bufferedReader.readLine()).equals("&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:")){
                            todo+=("\n"+data);

                        }
                        finish=true;
                        break;
                }
                if (finish){
                    LocalDate localDate=LocalDate.parse(dateString,dtf);

                    toDoItems.add(new ToDoItem(title,localDate,todo));
                    count=0;
                    finish=false;
                }




            }

        }catch (Exception e){
            System.out.println(e.getMessage());

            if (bufferedReader!=null){
                bufferedReader.close();
            }
        }





    }

    //when exit the progrm we save the data to txt file from txt to our observableArrayList
    public void saveTodoItems() throws IOException{

        Path path=Paths.get(fileName);
        BufferedWriter bufferedWriter=Files.newBufferedWriter(path);
        Iterator<ToDoItem> itemIterator=toDoItems.iterator();
while(true) {
    try {
        while (itemIterator.hasNext()) {
            ToDoItem toDoItem = itemIterator.next();
            bufferedWriter.write(toDoItem.getTitle());
            bufferedWriter.newLine();
            bufferedWriter.write(toDoItem.getDate().toString());
            bufferedWriter.newLine();
            bufferedWriter.write(toDoItem.getTodo());
            bufferedWriter.newLine();
            bufferedWriter.write("&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:&:");
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        break;


    } catch (Exception e) {

        System.out.println(e.getCause());
        System.out.println(e.getMessage());
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }
}

    }
    //add data to the our observableArrayList
    public boolean addNewData(ToDoItem toDoItem){

        for (ToDoItem item:toDoItems){
            if (item.getTitle().equals(toDoItem.getTitle())){
                return true;
            }
        }

        toDoItems.add(toDoItem);
        return false;
    }


    //delte item from our observableArrayList
    public void deleteItem(ToDoItem toDoItem){

        for (int i=0;i<toDoItems.toArray().length;i++){
            if (toDoItems.toArray()[i]==toDoItem){
                toDoItems.remove(i);
            }
        }

    }
}
