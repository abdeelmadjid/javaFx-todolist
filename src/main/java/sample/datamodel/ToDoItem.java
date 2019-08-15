package sample.datamodel;

import java.time.LocalDate;

public class ToDoItem {
    private String title;
    private LocalDate date;
    private String todo;


//this is the todoData
    public ToDoItem(String title, LocalDate date, String todo) {
        this.title = title;
        this.date = date;
        this.todo = todo;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }


}
