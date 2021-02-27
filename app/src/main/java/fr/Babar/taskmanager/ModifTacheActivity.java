
package fr.Babar.taskmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

public class ModifTacheActivity extends AppCompatActivity {
    private AccesLocalDB accesLocalDB;
    private RecyclerView RVTask;
    private List<Task> mListTask;
    private ConstraintLayout containerView;
    private int permissionAgenda = 0; /* 0 pas de permission; 1 permission ecriture accordé*/

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //on peut écrire dans l'agenda
                permissionAgenda = 1;

            } else if (!shouldShowRequestPermissionRationale(permissions[0])) {
                displayOptions();
            } else {
                explain();
            }
        }

        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void displayOptions() {
        Snackbar.make(containerView, "Vous avez désactivé la permission", Snackbar.LENGTH_LONG).setAction("Paramètres", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                final Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }).show();
    }

    private void askForPermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, 2);
    }

    private void explain() {
        Snackbar.make(containerView, "Cette permission est nécessaire pour appeler", Snackbar.LENGTH_LONG).setAction("Activer", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForPermission();
            }
        }).show();
    }

    private void verifPermission() {

        /* on vérifie que la permission a été accordée */
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            /* on vérifie que la permission a déjà été demandé */
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {
                /* si accordé mais déjà refusée */
                explain();
            } else {
                /* si jamais demandé */
                askForPermission();
            }
        } else {
            /* si on a la permission on fait le taf !*/
            permissionAgenda = 1;
        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_tache);
        containerView = findViewById(R.id.containerModif);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        mListTask = accesLocalDB.recupereTask("") ;

        if(mListTask == null){
            finish();
        }
        /*Affichage des Categories */
        RVTask = findViewById(R.id.RVModifListTache);

        // specify an adapter (see also next example)
        verifPermission();
        RecyclerViewAdapterModifTask mAdapter = new RecyclerViewAdapterModifTask(mListTask,permissionAgenda);
        RVTask.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RVTask.setLayoutManager(layoutManager);
        //RVCategorie.setItemAnimator(new DefaultItemAnimator());
        RVTask.setAdapter(mAdapter);

    }
}
