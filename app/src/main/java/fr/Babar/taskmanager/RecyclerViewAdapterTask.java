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

import static android.graphics.Color.*;

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
        private ConstraintLayout layoutOfItem;

        public MyViewHolder(final View arg_itemView) {
            super(arg_itemView);
            name = arg_itemView.findViewById(R.id.nomTache);
            description = arg_itemView.findViewById(R.id.descriptionTache);
            echeance = arg_itemView.findViewById(R.id.echeanceTache);
            itemLayout = arg_itemView.findViewById(R.id.itemview_layout);
            checkBoxTache = arg_itemView.findViewById(R.id.checkBoxTache);
            layoutOfItem = arg_itemView.findViewById(R.id.item_layout_cl);

            /* Gestion du click sur la tache */
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
                    String strRessource = view.getResources().getString(R.string.str_recurence);
                    if (mTask.getRecurence().equals(strRessource)){
                        recurenceDialog.setText("Aucune");
                    }
                    else{
                        recurenceDialog.setText(mTask.getRecurence());
                    }

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
                        mTask.setSelectionne(true,checkBoxTache);
                    }else{
                        mTask.setSelectionne(false, checkBoxTache);
                    }
                }
            });
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