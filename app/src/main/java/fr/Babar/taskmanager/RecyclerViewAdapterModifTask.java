package fr.Babar.taskmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import fr.Babar.taskmanager.model.Task;

public class RecyclerViewAdapterModifTask extends RecyclerView.Adapter<RecyclerViewAdapterModifTask.MyViewHolder> {
    private List<Task> taskList;
    //private ClickListener clickListener;
    RecyclerViewAdapterModifTask(List<Task> mItemList){
        this.taskList = mItemList;
    }
    @NonNull
    @Override
    public RecyclerViewAdapterModifTask.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_task,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapterModifTask.MyViewHolder holder, final int position) {
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
        private ConstraintLayout layoutOfItem;

        public MyViewHolder(final View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomTache);
            description = arg_itemView.findViewById(R.id.descriptionTache);
            echeance = arg_itemView.findViewById(R.id.echeanceTache);
            itemLayout = arg_itemView.findViewById(R.id.itemview_layout);
            checkBoxTache = arg_itemView.findViewById(R.id.checkBoxTache);
            //suppression de la check box
            checkBoxTache.setVisibility(View.INVISIBLE);
            layoutOfItem = arg_itemView.findViewById(R.id.item_layout_cl);

        }
        @SuppressLint("ResourceAsColor")
        public void setTask (Task arg_task){
            final Calendar c = Calendar.getInstance();
            double totalDate = c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) +1 ) * 100 + c.get(Calendar.DAY_OF_MONTH);
            String localEcheance;
            double dateTache ;

            mTask = arg_task;
            localEcheance = mTask.getEcheance().substring(0,8);
            dateTache = new Double(localEcheance);

            name.setText(mTask.getNom());
            description.setText(mTask.getDescription());
            echeance.setText(mTask.getEcheanceFormattee());
            if (dateTache > totalDate){
                /* on est en retard */
                layoutOfItem.setBackgroundColor(Color.WHITE);

            }
            else{
                name.setTextColor(Color.WHITE);
                description.setTextColor(Color.WHITE);
                echeance.setTextColor(Color.WHITE);
            }

        }
    }
}