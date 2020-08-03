package fr.Babar.taskmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        holder.name.setText(tache.getNom());
        holder.description.setText(tache.getDescription());

    }
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name,description;
        private LinearLayout itemLayout;
        public MyViewHolder(View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomTache);
            description = arg_itemView.findViewById(R.id.descriptionTache);
            itemLayout =  arg_itemView.findViewById(R.id.itemview_layout);
        }
    }
}