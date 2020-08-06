package fr.Babar.taskmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Task;

public class RecyclerViewAdapterTask extends RecyclerView.Adapter<RecyclerViewAdapterTask.MyViewHolder> {
    private List<Task> taskList;
    //private ClickListener clickListener;
    RecyclerViewAdapterTask(List<Task> mItemList){
        this.taskList = mItemList;
    }
    @NonNull
    @Override
    public RecyclerViewAdapterTask.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_task,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapterTask.MyViewHolder holder, final int position) {
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

        public MyViewHolder(final View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomTache);
            description = arg_itemView.findViewById(R.id.descriptionTache);
            echeance = arg_itemView.findViewById(R.id.echeanceTache);
            itemLayout = arg_itemView.findViewById(R.id.itemview_layout);
            checkBoxTache = arg_itemView.findViewById(R.id.checkBoxTache);

            arg_itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    // 2. Chain together various setter methods to set the dialog characteristics
                    //builder.setMessage("Super Message");
                    builder.setTitle(R.string.str_details);
                    // Ajout du layout
                    ViewGroup viewGroup = arg_itemView.findViewById(android.R.id.content);
                    View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_view_tache, viewGroup, false);
                    builder.setView(dialogView);


                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    // Remplissage des valeurs
                    //nom
                    TextView nomDialog = dialogView.findViewById(R.id.textview_dialog_nom);
                    nomDialog.setText(mTask.getNom());
                    // description
                    TextView descriptionDialog = dialogView.findViewById(R.id.textview_dialog_description);
                    descriptionDialog.setText(mTask.getDescription());
                    // Echéance
                    TextView echeanceDialog = dialogView.findViewById(R.id.textview_dialog_echeance);
                    echeanceDialog.setText(mTask.getEcheanceFormattee());
                    // Catégorie
                    TextView categorieDialog = dialogView.findViewById(R.id.textview_dialog_categorie);
                    echeanceDialog.setText(mTask.getCategorie());
                    // récurence
                    TextView recurenceDialog = dialogView.findViewById(R.id.textview_dialog_recurence);
                    recurenceDialog.setText(mTask.getRecurence());
                    //Urgence
                    TextView urgenceDialog = dialogView.findViewById(R.id.textview_dialog_urgence);
                    urgenceDialog.setText(mTask.getUrgence());

                }
            });
            checkBoxTache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
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
            name.setText(mTask.getNom());
            description.setText(mTask.getDescription());
            echeance.setText(mTask.getEcheanceFormattee());
        }
    }
}