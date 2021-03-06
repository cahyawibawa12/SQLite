package id.progmoblanjut.todolist_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import id.progmoblanjut.todolist_sqlite.Model.ToDoModel;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "noteListDatabase";
    private static final String NOTE_TABLE = "note";
    private static final String ID = "id";
    private static final String NOTE = "isi";
    private static final String STATUS = "status";
    private static final String CREATE_TODO_TABLE = "CREATE TABLE " + NOTE_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase db;
    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TODO_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
        // Create tables again
        onCreate(db);
    }
    public void openDatabase() {
        db = this.getWritableDatabase();
    }
    public void insertTask(ToDoModel task){
        ContentValues cv = new ContentValues();
        cv.put(NOTE, task.getTask());
        cv.put(STATUS, 0);
        db.insert(NOTE_TABLE, null, cv);
    }
    public List<ToDoModel> getAllTasks(){
        List<ToDoModel> taskList = new ArrayList<>();
        Cursor cur = null;
        db.beginTransaction();
        try{
            cur = db.query(NOTE_TABLE, null, null, null, null, null, null, null);
            if(cur != null){
                if(cur.moveToFirst()){
                    do{
                        ToDoModel task = new ToDoModel();
                        task.setId(cur.getInt(cur.getColumnIndex(ID)));
                    task.setTask(cur.getString(cur.getColumnIndex(NOTE)));
                        task.setStatus(cur.getInt(cur.getColumnIndex(STATUS)));
                        taskList.add(task);
                    }
                    while(cur.moveToNext());
                }
            }
        }
        finally {
            db.endTransaction();
            assert cur != null;
            cur.close();
        }
        return taskList;
    }

    public void updateStatus(int id, int status){
        ContentValues cv = new ContentValues();
        cv.put(STATUS, status);
        db.update(NOTE_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void updateTask(int id, String task) {
        ContentValues cv = new ContentValues();
        cv.put(NOTE, task);
        db.update(NOTE_TABLE, cv, ID + "= ?", new String[] {String.valueOf(id)});
    }
    public void deleteTask(int id){
        db.delete(NOTE_TABLE, ID + "= ?", new String[] {String.valueOf(id)});
    }
}
