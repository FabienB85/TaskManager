package fr.Babar.taskmanager.outils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.Babar.taskmanager.R;
import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.model.Task;

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
        String requete = "INSERT INTO taches (nom,description,duree,echeance,categorie,recurence,urgence,idevent) VALUES "
                +"(\"" + arg_task.getNom()
                + "\",\"" + arg_task.getDescription()
                + "\",\"" + arg_task.getDuree()
                +"\",\"" + arg_task.getEcheance()
                +"\",\"" + arg_task.getCategorie()
                +"\",\"" + arg_task.getRecurence()
                +"\",\"" + arg_task.getUrgence()
                +"\",\"" + arg_task.getEventId().toString()

                +"\");";
        localDB.execSQL(requete);
        accesDB.close();
    }

    /**
     * REcuperation de toutes les taches de la base de donnes
     * @return task retourne une tache
     */

    public List<Task> recupereTask (String arg_categorie){
        Task localTask;
        List<Task> listeRetourTask = new ArrayList<>();
        int nbEntree;
        String requete;
        if (arg_categorie.equals("")){
            requete =  "SELECT * FROM taches ORDER BY echeance;";
        }
        else{
            requete =  "SELECT * FROM taches WHERE categorie = '";
            requete = requete + arg_categorie + "' ORDER BY echeance;";
        }


        /**
         * Le curseur permet de lire ligne à lignes le resultat de la requete
         */
        localDB = accesDB.getReadableDatabase();
        Cursor cursor;
        try {
            cursor = localDB.rawQuery(requete, null);
        }
        catch (Exception e){
            cursor = null;
        }
        /**
         * se possitionner sur la derniere inscrption
         */
        nbEntree = cursor.getCount();
        if (nbEntree == 0)
        {
            listeRetourTask = null;
        }
        else
        {
            cursor.moveToFirst();
            /* on parcours les entrées */
            for (int i = 0; i < nbEntree; i++) {
                String nom = cursor.getString(1);
                String description = cursor.getString(2);

                localTask = new Task(nom, description);
                localTask.setId(Integer.valueOf(cursor.getString(0)));
                localTask.setDuree(cursor.getString(3));
                localTask.setEcheance(cursor.getString(4));
                localTask.setCategorie(cursor.getString(5));
                localTask.setRecurence(cursor.getString(6));
                localTask.setUrgence(cursor.getString(7));
                //invite en 8
                localTask.setEventId(Integer.valueOf(cursor.getString(9)));

                listeRetourTask.add(localTask);
                cursor.moveToNext();
            }
        }

        cursor.close();
        accesDB.close();
        return listeRetourTask;
    }

    public List<Categorie> recupereCategories(){
        int nbEntree;
        String requete = "SELECT nom,description FROM categories;";
        List<Categorie> ListeCategories = new ArrayList<>();
        Categorie mCategorie = new Categorie();

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
                ListeCategories.add(new Categorie(cursor.getString(0),cursor.getString(1)));
                cursor.moveToNext();
            }
        }
        /* on ferme tout le monde */
        cursor.close();
        accesDB.close();
        return ListeCategories;
    }

    public void supprimeCategorie(Categorie arg_categorie) {
        String requete = "DELETE FROM categories WHERE nom = \"" + arg_categorie.getNom() + "\";";
        localDB = accesDB.getWritableDatabase();
        localDB.execSQL(requete);
        accesDB.close();
    }
    public void ajoutCategorieDansDB (Categorie arg_categorie){
        localDB = accesDB.getWritableDatabase();
        String requete = "INSERT INTO categories (nom,description) VALUES "
                +"(\"" + arg_categorie.getNom()
                + "\",\"" + arg_categorie.getDescription()
                +"\");";
        localDB.execSQL(requete);
        accesDB.close();
    }

    public void supprimeTask(Task arg_task) {
        String requete = "DELETE FROM taches WHERE nom = \"" + arg_task.getNom() + "\";";
        localDB = accesDB.getWritableDatabase();
        localDB.execSQL(requete);
        accesDB.close();
    }

    public void modifieTask(Task arg_task){
        String requete = "UPDATE taches SET "+
                "nom = \""  + arg_task.getNom() + "\"," +
                "description = \""  + arg_task.getDescription() + "\"," +
                "duree = \""  + arg_task.getDuree() + "\"," +
                "echeance = \""  + arg_task.getEcheance() + "\"," +
                "categorie = \""  + arg_task.getCategorie() + "\"," +
                "recurence = \""  + arg_task.getRecurence() + "\"," +
                "urgence = \""  + arg_task.getUrgence() + "\", " +
                "idevent = \"" + arg_task.getEventId().toString() + "\" " +
                "WHERE id = \""  + arg_task.getId().toString() + "\";" ;
        localDB = accesDB.getWritableDatabase();
        localDB.execSQL(requete);
        accesDB.close();
    }

}
