package fr.Babar.taskmanager.outils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.time.Duration;
import java.util.Date;

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
     * @param arg_contexte
     */
    public AccesLocalDB(Context arg_contexte) {
        this.contexte = arg_contexte;
        nomDB = contexte.getString(R.string.nom_DB);
        accesDB = new MySQLiteOpenHelper(contexte,nomDB,null, version);
    }

    /**
     * Ajout d'une Tache dans la base de donnees
     * @param arg_task
     */
    public void ajoutTaskDansDB (Task arg_task){
        localDB = accesDB.getWritableDatabase();
        String requete = "insert into taches (nom,description,duree,echeance,categorie) values "
                +"(\"" + arg_task.getNom()
                + "\",\"" + arg_task.getDescription()
                + "\",\"" + arg_task.getDuree()
                + "\",\"" + arg_task.getEcheance()
                +"\",\"" + arg_task.getCategorie()
                +"\")";
        localDB.execSQL(requete);

    }

    /**
     * REcuperation des taches de la base de donnes
     * @return
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
        return localTask;
    }
}
