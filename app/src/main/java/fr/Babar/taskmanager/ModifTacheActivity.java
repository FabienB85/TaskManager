package fr.Babar.taskmanager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fr.Babar.taskmanager.outils.AccesLocalDB;

public class ModifTacheActivity extends AppCompatActivity {
    private AccesLocalDB accesLocalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_tache);

    }
}
