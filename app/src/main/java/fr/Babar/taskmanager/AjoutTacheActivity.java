package fr.Babar.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class AjoutTacheActivity extends AppCompatActivity {
    private Spinner spinnerCategorie;
    private AccesLocalDB accesLocalDB ;
    private Button btnAjoutTache;
    private EditText editTextNom;
    private EditText editTextDescription;
    private EditText editTextDuree;
    private EditText editTextDateEcheance;
    private EditText editTextHeureEcheance;
    private Spinner spinnerUrgence;
    private TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_tache);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        /* Récupération de tous les éléments déclarés dans le layout */
        editTextNom = (EditText)findViewById(R.id.editTextNomTache);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        editTextDateEcheance = (EditText)findViewById(R.id.editTextDateEchance);
        editTextDuree = (EditText)findViewById(R.id.editTextDuree);
        editTextHeureEcheance = (EditText)findViewById(R.id.editTextTimeEcheance);

        //Récupération du Spinner déclaré dans le layout
        spinnerCategorie = (Spinner) findViewById(R.id.spinnerCategorie);
        spinnerUrgence = (Spinner) findViewById(R.id.spinnerUrgence);

        test = (TextView) findViewById(R.id.labelNom) ;

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

       /* définition du spinner (menu déroulant pour les catégories) */

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List<String> categories = accesLocalDB.recupereCategories();
        if (categories == null){
            categories = new ArrayList<>();
            categories.add("Erreur");
        }else
        {
            /* nothing to do*/
        }

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter( this,android.R.layout.simple_spinner_item,categories);

        /* On definit une présentation du spinner quand il est déroulé         (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerCategorie.setAdapter(adapter);

        /* Definition du spinner pour l'urgence / gravité */
        // création de la liste
        List<String> listUrgences = new ArrayList<>();
        listUrgences.add("Normal");
        listUrgences.add("Majeur");
        listUrgences.add("Mineur");
        // ajout de l'adapteur
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,listUrgences);
        // définition de la présentation
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //on passe l'adapteur au spinner
        spinnerUrgence.setAdapter(adapter2);

        /* traitement du bouton Ajout Tache */
        btnAjoutTache = (Button)this.findViewById(R.id.btnAjoutTache);
        btnAjoutTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task taskAAjouter = new Task("Defaut", "default");
                taskAAjouter.setNom(editTextNom.getText().toString());
                taskAAjouter.setDescription(editTextDescription.getText().toString());
                taskAAjouter.setCategorie(spinnerCategorie.getSelectedItem().toString());
                taskAAjouter.setUrgence(spinnerUrgence.getSelectedItem().toString());
                //accesLocalDB.ajoutTaskDansDB(taskAAjouter);
            }

        });

    }
}