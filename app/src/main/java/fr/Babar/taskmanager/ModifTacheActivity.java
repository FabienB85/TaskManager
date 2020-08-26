package fr.Babar.taskmanager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class ModifTacheActivity extends AppCompatActivity {
    private AccesLocalDB accesLocalDB;
    private RecyclerView RVTask;
    private List<Task> mListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_tache);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        mListTask = accesLocalDB.recupereTask("") ;

        if(mListTask == null){
            finish();
        }
        /*Affichage des Categories */
        RVTask = findViewById(R.id.RVModifListTache);

        // specify an adapter (see also next example)
        RecyclerViewAdapterModifTask mAdapter = new RecyclerViewAdapterModifTask(mListTask);
        RVTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RVTask.setLayoutManager(layoutManager);
        //RVCategorie.setItemAnimator(new DefaultItemAnimator());
        RVTask.setAdapter(mAdapter);

    }
}
