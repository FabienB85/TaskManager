package fr.Babar.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class GestionCategorieActivity extends AppCompatActivity {
    private EditText editTextNomCategorie;
    private EditText editTextDescriptionCategorie;
    private Button btnAjout;
    private RecyclerView RVCategorie;
    private Button btnSuppr;
    private AccesLocalDB accesLocalDB;
    private List<Categorie> mListCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categorie);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        /* Récupération de tous les éléments déclarés dans le layout */
        editTextNomCategorie = findViewById(R.id.editTextNomCategorie);
        editTextDescriptionCategorie = findViewById(R.id.editTextDescriptionCategorie);
        RVCategorie = findViewById(R.id.RVListCategorie);
        btnAjout = findViewById(R.id.btnAjoutCategorie);
        btnSuppr = findViewById(R.id.btnSupprimerCategorie);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        mListCategorie = accesLocalDB.recupereCategories() ;
        /*Affichage des Categories */
        RVCategorie = findViewById(R.id.RVListCategorie);
        // specify an adapter (see also next example)
        RecyclerViewAdapterCategorie mAdapter = new RecyclerViewAdapterCategorie(mListCategorie);
        RVCategorie.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RVCategorie.setLayoutManager(layoutManager);
        //RVCategorie.setItemAnimator(new DefaultItemAnimator());
        RVCategorie.setAdapter(mAdapter);

        /* traitement du bouton Ajout Categorie */
        btnAjout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageToast;
                Categorie mCategorie;
                boolean ok = false;
                messageToast = getString(R.string.str_categorie_ajoutee);

                if (editTextNomCategorie.getText().toString().equals("")){
                    messageToast = getString(R.string.str_nom_categorie_vide);
                }
                else{
                    if(editTextDescriptionCategorie.getText().toString().equals("")){
                        messageToast = getString(R.string.str_description_categorie_vide);
                    }
                    else{
                        mCategorie = new Categorie(editTextNomCategorie.getText().toString(),
                                editTextDescriptionCategorie.getText().toString());
                        accesLocalDB.ajoutCategoriekDansDB(mCategorie);
                        ok = true;
                    }
                }

                // petit message pour dire que la commande est prise en compte
                Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                toast.show();
                // termine l'activity
                if (ok){
                    finish();
                }

            }

        });

        /* Traitement du bouton supprimer*/
        btnSuppr.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String messageToast;
                messageToast = getString(R.string.str_categorie_supprimee);
                for (int i = 0; i < mListCategorie.size(); i++){
                    if (mListCategorie.get(i).getSelectionne()){
                        accesLocalDB.supprimeCategorie(mListCategorie.get(i));
                    }
                }
                // petit message pour dire que la commande est prise en compte
                Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                toast.show();
                // termine l'activity
                finish();
            }
        });

    }
}
