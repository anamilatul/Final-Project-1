package me.anamila.finalprojek1_todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CostumTask costumTask;
    private SQLiteDatabaseHandler db;
    private RecyclerView recyclerView;
    private ArrayList<Task> tasks;
    private Button buttonAdd;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.rvTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = new SQLiteDatabaseHandler(this);

        tasks = (ArrayList<Task>) db.getAllTasks();
        costumTask = new CostumTask(tasks,db);
        recyclerView.setAdapter(costumTask);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.add_popup,null,false);
                popupWindow = new PopupWindow(view, ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT,true);
                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);

                final EditText write = view.findViewById(R.id.write);
                Button btnSave = view.findViewById(R.id.btnSave);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.addTask(new Task(write.getText().toString()));
                        if(costumTask==null){
                            costumTask = new CostumTask(tasks,db);
                            recyclerView.setAdapter(costumTask);
                        }
                        costumTask.tasks = (ArrayList<Task>) db.getAllTasks();
                        recyclerView.getAdapter().notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
}