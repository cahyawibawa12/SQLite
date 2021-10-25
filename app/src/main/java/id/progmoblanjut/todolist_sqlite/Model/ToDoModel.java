package id.progmoblanjut.todolist_sqlite.Model;

public class ToDoModel {
    private int id, status;
    private String isi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTask() {
        return isi;
    }

    public void setTask(String isi) {
        this.isi = isi;
    }
}
