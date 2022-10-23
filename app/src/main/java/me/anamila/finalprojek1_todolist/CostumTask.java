package me.anamila.finalprojek1_todolist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class CostumTask extends RecyclerView.Adapter<CostumTask.CostumTaskViewHolder> {
    ArrayList<Task>tasks;
    SQLiteDatabaseHandler db;

    public CostumTask(ArrayList<Task> tasks, SQLiteDatabaseHandler db) {
        this.tasks = tasks;
        this.db = db;
    }

    @NonNull
    @Override
    public CostumTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_item,parent,false);
        return new CostumTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostumTaskViewHolder holder, int position) {
        holder.tvTasks.setText(tasks.get(position).getTaskName());
        holder.btnTaskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener;
                dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case DialogInterface.BUTTON_POSITIVE:
                                db.deleteTask(tasks.get(holder.getAdapterPosition()));
                                tasks = (ArrayList<Task>) db.getAllTasks();
                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("I am sure that the task has been completed")
                        .setPositiveButton("Yes",dialogClickListener)
                        .setNegativeButton("No",dialogClickListener)
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class CostumTaskViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTasks;
        private Button btnTaskCompleted;

        public CostumTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTasks = itemView.findViewById(R.id.tvTasks);
            btnTaskCompleted = itemView.findViewById(R.id.btnTaskCompleted);
        }
    }
}
