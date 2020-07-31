package fr.Babar.taskmanager.outils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.Babar.taskmanager.R;
import fr.Babar.taskmanager.model.Task;

import static android.provider.Settings.System.getString;

/**
 * Classe d'accès local à la database
 */
public class AccesLocalDB {
    // propriete
    private Context contexte;
    private String nomDB;
    private Integer version = 1; // TODO mettre ça dans les parametres
    private MySQLiteOpenHelper accesDB;
    private SQLiteDatabase localDB;

    /**
     * Constructeur de l'acces a la database
     * @param arg_contexte contexte de l'application
     */
    public AccesLocalDB(Context arg_contexte) {
        this.contexte = arg_contexte;
        nomDB = contexte.getString(R.string.nom_DB);
        accesDB = new MySQLiteOpenHelper(contexte,nomDB,null, version);
    }

    /**
     * Ajout d'une Tache dans la base de donnees
     * @param arg_task tache à ajouter dans la base de donnée
     */
    public void ajoutTaskDansDB (Task arg_task){
        localDB = accesDB.getWritableDatabase();
        String requete = "insert into taches (nom,description,duree,categorie,recurence,urgence) values "
                +"(\"" + arg_task.getNom()
                + "\",\"" + arg_task.getDescription()
                + "\",\"" + arg_task.getDuree()

                +"\",\"" + arg_task.getCategorie()
                +"\",\"" + arg_task.getRecurence()
                +"\",\"" + arg_task.getUrgence()
                +"\")";
        //+ "\",\"" + arg_task.getEcheance()
        localDB.execSQL(requete);
        accesDB.close();

    }

    /**
     * REcuperation des taches de la base de donnes
     * @return task retourne une tache
     */
    public Task recupereTask (){
        localDB = accesDB.getReadableDatabase();
        Task localTask = null;
        String requete =  "Select * from taches";
        /**
         * Le curseur permet de lire ligne à lignes le resultat de la requete
         */
        Cursor cursor = localDB.rawQuery(requete,null);
        /**
         * se possitionner sur la derniere inscrption
         */
        cursor.moveToLast();
        if (!cursor.isAfterLast()){
            Date date = new Date(); // TODO faire conversion de date
            String nom = cursor.getString(0);
            String description = cursor.getString(1);
            localTask = new Task(nom, description);
            localTask.setCategorie(cursor.getString(4));
            localTask.setEcheance(date);
        }
        cursor.close();
        return localTask;
    }
    public List<String> recupereCategories(){
        int nbEntree;
        String requete = "SELECT nom FROM categories;";
        List<String> ListeCategories = new ArrayList<>();

        /* on récupère les données de la base de données */
        localDB = accesDB.getReadableDatabase();
        Cursor cursor = localDB.rawQuery(requete,null);
        nbEntree = cursor.getCount();
        if (nbEntree == 0)
        {
            ListeCategories = null;
        }
        else
        {
            /* on se positionne sur la première entrée */
            cursor.moveToFirst();
            /* on parcours les entrées */
            for (int i = 0; i < nbEntree; i++) {
                ListeCategories.add(cursor.getString(0));
                cursor.moveToNext();
            }
        }
        /* on ferme tout le monde */
        cursor.close();
        accesDB.close();
        return ListeCategories;
    }
}
