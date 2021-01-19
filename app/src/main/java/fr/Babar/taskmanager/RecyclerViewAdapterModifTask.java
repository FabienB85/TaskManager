package fr.Babar.taskmanager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class RecyclerViewAdapterModifTask extends RecyclerView.Adapter<RecyclerViewAdapterModifTask.MyViewHolder> {
    private List<Task> taskList;

    //private ClickListener clickListener;
    RecyclerViewAdapterModifTask(List<Task> mItemList) {
        this.taskList = mItemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterModifTask.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_task, parent, false);
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
        private TextView echeanceModifDateTache;
        private TextView echeanceModifTimeTache;
        private int mYear, mMonth, mDay, mHour, mMinute;
        private EditText nomModifTache;
        private EditText descriptionModifTache;
        private EditText editTextModifDuree;
        private Spinner categorieModifTache;
        private Spinner recurenceModifTache;
        private Spinner urgenceModifTache;
        private Spinner qualificatifDureeModifTache;
        private View dialogView;

        private AccesLocalDB accesLocalDB;

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


            accesLocalDB = new AccesLocalDB(arg_itemView.getContext().getApplicationContext());

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
                    dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_view_modif_tache, viewGroup, false);
                    builder.setView(dialogView);


                    // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    // Remplissage des valeurs
                    //nom
                    nomModifTache = dialogView.findViewById(R.id.editTextModifNomTache);
                    nomModifTache.setText(mTask.getNom());
                    // description
                    descriptionModifTache = dialogView.findViewById(R.id.editTextModifDescription);
                    descriptionModifTache.setText(mTask.getDescription());
                    // Echéance
                    echeanceModifDateTache = dialogView.findViewById(R.id.textViewModifDateEchance);
                    echeanceModifDateTache.setText(mTask.getDateEcheance());
                    echeanceModifDateTache.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (view == echeanceModifDateTache) {

                                // Get Current Date
                                final Calendar c = Calendar.getInstance();
                                mYear = c.get(Calendar.YEAR);
                                mMonth = c.get(Calendar.MONTH);
                                mDay = c.get(Calendar.DAY_OF_MONTH);

                                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                                        new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year,
                                                                  int monthOfYear, int dayOfMonth) {

                                                echeanceModifDateTache.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                            }
                                        }, mYear, mMonth, mDay);
                                datePickerDialog.show();
                            }

                        }
                    });

                    echeanceModifTimeTache = dialogView.findViewById(R.id.textViewModifTimeEcheance);
                    echeanceModifTimeTache.setText(mTask.getTimeEcheance());
                    echeanceModifTimeTache.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (view == echeanceModifTimeTache) {
                                // Get Current Time
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);
                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                echeanceModifTimeTache.setText(hourOfDay + ":" + minute);
                                            }
                                        }, mHour, mMinute, true);
                                timePickerDialog.show();
                            }
                        }
                    });
                    // Durée
                    editTextModifDuree = dialogView.findViewById(R.id.editTextModifDuree);
                    editTextModifDuree.setText(mTask.getNombreDuree());
                    qualificatifDureeModifTache = dialogView.findViewById(R.id.spinnerModifQualificatifDuree);
                    // TODO faire en sorte que cette liste soit une propriete de la classe
                    List<String> listQualificatifDuree = new ArrayList<>();
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_minute));
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_hour));
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_day));
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_week));
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_month));
                    listQualificatifDuree.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_year));
                    ArrayAdapter adapterQualificatifDuree = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, listQualificatifDuree);
                    adapterQualificatifDuree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    qualificatifDureeModifTache.setAdapter(adapterQualificatifDuree);
                    for (int j = 0; j < listQualificatifDuree.size(); j++) {
                        String currentQualificatif = listQualificatifDuree.get(j);
                        if (currentQualificatif.equals(mTask.getQualificatifDuree())) {
                            qualificatifDureeModifTache.setSelection(j);
                        }
                    }

                    // Catégorie
                    categorieModifTache = dialogView.findViewById(R.id.spinnerModifCategorie);
                    List<Categorie> categories = accesLocalDB.recupereCategories();
                    if (categories == null) {
                        categories = new ArrayList<>();
                        categories.add(new Categorie("Erreur", "Erreur"));
                    } else {

                        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                        un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
                        Avec la liste des elements (exemple) */
                        ArrayAdapter adapterCategorie = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, categories);

                         /* On definit une présentation du spinner quand il est déroulé
                        (android.R.layout.simple_spinner_dropdown_item) */
                        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Enfin on passe l'adapter au Spinner et c'est tout
                        categorieModifTache.setAdapter(adapterCategorie);

                        for (int j = 0; j < categories.size(); j++) {
                            String categorieCurrent = categories.get(j).getNom();
                            if (categorieCurrent.equals(mTask.getCategorie())) {
                                categorieModifTache.setSelection(j);
                            } else {
                                /* nothing to do*/
                            }
                        }
                    }

                    // récurence
                    recurenceModifTache = dialogView.findViewById(R.id.spinnerModifRecurence);
                    /* Création du spinner pour la récurence */
                    // TODO faire en sorte que cette liste soit une propriete de la classe
                    List<String> listRecurence = new ArrayList<>();
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_recurence));
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_1heure));
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_1jour));
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_1semaine));
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_1mois));
                    listRecurence.add(view.getContext().getApplicationContext().getResources().getString(R.string.str_1annee));


                    /* On definit une présentation du spinner quand il est déroulé */
                    ArrayAdapter adapterRecurence = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, listRecurence);
                    adapterRecurence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    recurenceModifTache.setAdapter(adapterRecurence);
                    for (int j = 0; j < listRecurence.size(); j++) {
                        String currentRecurence = listRecurence.get(j);
                        if (currentRecurence.equals(mTask.getRecurence())) {
                            recurenceModifTache.setSelection(j);
                        }
                    }

                    //Urgence
                    urgenceModifTache = dialogView.findViewById(R.id.spinnerModifUrgence);
                    /*urgenceModifTache.setText(mTask.getUrgence());*/
                    /* Definition du spinner pour l'urgence / gravité */
                    // création de la liste
                    List<String> listUrgences = new ArrayList<>();
                    listUrgences.add("Normal");
                    listUrgences.add("Majeur");
                    listUrgences.add("Mineur");

                    // ajout de l'adapteur
                    ArrayAdapter adapterUrgence = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, listUrgences);
                    // définition de la présentation
                    adapterUrgence.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    //on passe l'adapteur au spinner
                    urgenceModifTache.setAdapter(adapterUrgence);
                    for (int j = 0; j < listUrgences.size(); j++) {
                        String currentUrgence = listUrgences.get(j);
                        if (currentUrgence.equals(mTask.getUrgence())) {
                            urgenceModifTache.setSelection(j);
                        }
                    }

                    Button modifButton = dialogView.findViewById(R.id.btnModifTache);
                    modifButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String messageToast;
                            messageToast = view.getContext().getApplicationContext().getResources().getString(R.string.str_tache_modif);
                            if (nomModifTache.getText().toString().equals("")) {
                                messageToast = view.getContext().getApplicationContext().getResources().getString(R.string.str_nom_tache_vide);
                            } else {
                                messageToast = nomModifTache.getText().toString();
                                if (descriptionModifTache.getText().toString().equals("")) {
                                    messageToast = view.getContext().getApplicationContext().getResources().getString(R.string.str_description_tache_vide);
                                } else {
                                    //Task taskAAjouter = new Task("Defaut", "default");
                                    mTask.setNom(nomModifTache.getText().toString());
                                    mTask.setDescription(descriptionModifTache.getText().toString());
                                    mTask.setCategorie(categorieModifTache.getSelectedItem().toString());
                                    mTask.setUrgence(urgenceModifTache.getSelectedItem().toString());
                                    mTask.setDuree(editTextModifDuree.getText().toString() + ":" + qualificatifDureeModifTache.getSelectedItem().toString());
                                    mTask.setEcheance(echeanceModifDateTache.getText().toString(), echeanceModifTimeTache.getText().toString());
                                    mTask.setRecurence(recurenceModifTache.getSelectedItem().toString());
                                    mTask.setUrgence(urgenceModifTache.getSelectedItem().toString());
                                    accesLocalDB.modifieTask(mTask);
                                }
                            }
                            // petit message pour dire que la commande est prise en compte
                            Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                            toast.show();
                            dialog.cancel();
                        }

                    });
                }
            });

        }

        @SuppressLint("ResourceAsColor")
        public void setTask(Task arg_task) {
            final Calendar c = Calendar.getInstance();
            double totalDate = c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
            String localEcheance;
            double dateTache;

            mTask = arg_task;
            localEcheance = mTask.getEcheance().substring(0, 8);
            dateTache = new Double(localEcheance);

            name.setText(mTask.getNom());
            description.setText(mTask.getDescription());
            echeance.setText(mTask.getEcheanceFormattee());
            if (dateTache > totalDate) {
                /* on est en retard */
                layoutOfItem.setBackgroundColor(Color.WHITE);

            } else {
                name.setTextColor(Color.WHITE);
                description.setTextColor(Color.WHITE);
                echeance.setTextColor(Color.WHITE);
            }

        }
    }
}