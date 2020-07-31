package fr.Babar.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class AjoutTacheActivity extends AppCompatActivity {
    private Spinner spinnerCategorie;
    private AccesLocalDB accesLocalDB;
    private Button btnAjoutTache;
    private EditText editTextNom;
    private EditText editTextDescription;
    private EditText editTextDuree;
    private TextView editTextDateEcheance;
    private TextView editTextHeureEcheance;
    private Spinner spinnerUrgence;
    private Spinner spinnerRecurence;
    private TextView test;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_tache);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        /* Récupération de tous les éléments déclarés dans le layout */
        editTextNom = (EditText) findViewById(R.id.editTextNomTache);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextDateEcheance = (TextView) findViewById(R.id.editTextDateEchance);
        editTextDuree = (EditText) findViewById(R.id.editTextDuree);
        editTextHeureEcheance = (TextView) findViewById(R.id.editTextTimeEcheance);

        //Récupération du Spinner déclaré dans le layout
        spinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
        spinnerRecurence = (Spinner) findViewById(R.id.spinnerRecurence);
        spinnerUrgence = (Spinner) findViewById(R.id.spinnerUrgence);

        //test = (TextView) findViewById(R.id.labelNom);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        /* définition du spinner (menu déroulant pour les catégories) */

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List<String> categories = accesLocalDB.recupereCategories();
        if (categories == null) {
            categories = new ArrayList<>();
            categories.add("Erreur");
        } else {
            /* nothing to do*/
        }

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        /* On definit une présentation du spinner quand il est déroulé
                (android.R.layout.simple_spinner_dropdown_item) */
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerCategorie.setAdapter(adapterCategorie);

        /* Création du spinner pour la récurence */
        List<String> listRecurence = new ArrayList<>();
        listRecurence.add("Récurence");
        listRecurence.add(getResources().getString(R.string.str_1heure));
        listRecurence.add(getResources().getString(R.string.str_1jour));
        listRecurence.add(getResources().getString(R.string.str_1semaine));
        listRecurence.add(getResources().getString(R.string.str_1mois));
        listRecurence.add(getResources().getString(R.string.str_1annee));

        /* On definit une présentation du spinner quand il est déroulé */
        ArrayAdapter adapterRecurence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listRecurence);
        adapterRecurence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecurence.setAdapter(adapterRecurence);

        /* Definition du spinner pour l'urgence / gravité */
        // création de la liste
        List<String> listUrgences = new ArrayList<>();
        listUrgences.add("Normal");
        listUrgences.add("Majeur");
        listUrgences.add("Mineur");
        // ajout de l'adapteur
        ArrayAdapter adapterUrgence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listUrgences);
        // définition de la présentation
        adapterUrgence.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //on passe l'adapteur au spinner
        spinnerUrgence.setAdapter(adapterUrgence);

        /* traitement du bouton Ajout Tache */
        btnAjoutTache = (Button) this.findViewById(R.id.btnAjoutTache);
        btnAjoutTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Tâche Ajoutée", Toast.LENGTH_SHORT);
                Task taskAAjouter = new Task("Defaut", "default");
                taskAAjouter.setNom(editTextNom.getText().toString());
                taskAAjouter.setDescription(editTextDescription.getText().toString());
                taskAAjouter.setCategorie(spinnerCategorie.getSelectedItem().toString());
                taskAAjouter.setUrgence(spinnerUrgence.getSelectedItem().toString());
                //accesLocalDB.ajoutTaskDansDB(taskAAjouter);

                toast.show();
            }

        });

        /* traitement du date picker */

        editTextDateEcheance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view == editTextDateEcheance) {

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

                                    editTextDateEcheance.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }

            }
        });
        editTextHeureEcheance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == editTextHeureEcheance) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    editTextHeureEcheance.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, true);
                    timePickerDialog.show();
                }
            }
        });
    }
}
