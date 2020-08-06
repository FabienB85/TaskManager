package fr.Babar.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import fr.Babar.taskmanager.outils.AccesLocalDB;

public class GestionCategorieActivity extends AppCompatActivity {
    private EditText editTextNomCategorie;
    private EditText editTextDescriptionCategorie;
    private Button btnAjout;
    private RecyclerView RVCategorie;
    private Button btnSuppr;
    private AccesLocalDB accesLocalDB;
    private TextView test;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categorie);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        /* Récupération de tous les éléments déclarés dans le layout */
        editTextNomCategorie = findViewById(R.id.editTextNomCategorie);
        editTextDescriptionCategorie = findViewById(R.id.editTextDescriptionCategorie);
       // RVCategorie = findViewById(R.id.RVListCategorie);
        btnAjout = findViewById(R.id.btnAjoutCategorie);
        btnSuppr = findViewById(R.id.btnSupprimerCategorie);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        /* traitement du bouton Ajout Categorie */
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageToast;
                messageToast = getString(R.string.str_categorie_ajoutee);
                // TODO creer class Categorie
                // TODO creer fonction accesLocalDB pour ajouter une catégorie

                // petit message pour dire que la commande est prise en compte
                Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                toast.show();
                // termine l'activity
                finish();
            }

        });

        /* Traitement du bouton supprimer*/
        btnSuppr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String messageToast;
                messageToast = getString(R.string.str_categorie_supprimee);
                // TODO creer class Categorie
                // TODO creer fonction accesLocalDB pour ajouter une catégorie

                // petit message pour dire que la commande est prise en compte
                Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                toast.show();
                // termine l'activity
                finish();
            }
        });

    }
}
