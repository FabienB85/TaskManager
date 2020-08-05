package fr.Babar.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Task;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Task> taskList;
    //private ClickListener clickListener;
    RecyclerViewAdapter(List<Task> mItemList){
        this.taskList = mItemList;
    }
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_task,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
        final Task tache = taskList.get(position);
        holder.setTask(tache);
    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, echeance;
        private CheckBox checkBoxTache;
        private Task mTask;
        private LinearLayout itemLayout;

        public MyViewHolder(View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomTache);
            description = arg_itemView.findViewById(R.id.descriptionTache);
            echeance = arg_itemView.findViewById(R.id.echeanceTache);
            itemLayout = arg_itemView.findViewById(R.id.itemview_layout);
            checkBoxTache = arg_itemView.findViewById(R.id.checkBoxTache);

            arg_itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(view.getContext(), mTask.getDescription(), Toast.LENGTH_SHORT);
                    toast.show(); // TODO remplacer Toast par un popup
                }
            });
            checkBoxTache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b == true){
                        // check !
                        mTask.setSelectionne(true);
                    }else{
                        mTask.setSelectionne(false);
                    }
                }
            });
        }
        public void setTask (Task arg_task){

            mTask = arg_task;
            String temp = mTask.getEcheance().substring(0,4) + "-" + mTask.getEcheance().substring(4,6)
                    + "-" + mTask.getEcheance().substring(6,8) + " " + mTask.getEcheance().substring(8,10)
                    + "H" + mTask.getEcheance().substring(10);
            name.setText(mTask.getNom());
            description.setText(mTask.getDescription());
            echeance.setText(temp);
        }
    }
}