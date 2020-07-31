package fr.Babar.taskmanager.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    /* creation de la table de tache */
    private String requeteCreation = "create table taches("
            + "id INTEGER NOT NULL UNIQUE,"
            + "nom TEXT NOT NULL,"
            + "description TEXT NOT NULL,"
            + "duree TEXT,"
            + "echeance TEXT,"
            + "categorie TEXT NOT NULL,"
            + "recurence TEXT,"
            + "urgence TEXT,"
            + "invite TEXT,"
            + "PRIMARY KEY(\"id\" AUTOINCREMENT));";
    /* creation de la table des categories */
    private String createCategories = "create table categories("
            + "id INTEGER NOT NULL UNIQUE,"
            + "nom TEXT NOT NULL,"
            + "description TEXT,"
            + "PRIMARY KEY(\"id\" AUTOINCREMENT));";
    /* Remplissage des categories */
    private String remplissageCategorieParDefaut1 = "INSERT INTO categories VALUES (1,'Professionnel','Ensemble des tâches professionnelles');";
    private String remplissageCategorieParDefaut2 = "INSERT INTO categories VALUES (2,'Personnel','Ensemble des tâches personnelles');";
    private String remplissageCategorieParDefaut3 = "INSERT INTO categories VALUES (3,'Autre','Ensemble des tâches inclassables');";

    /**
     * Constructeur de la classe
     * @param context contexte de l'application
     * @param name nom de la base
     * @param factory factory
     * @param version version de la base
     */
    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Fonction abstraite à surcharger
     * oncreate est appelée lors de la création de la base de donnée
     * @param sqLiteDatabase bose de donnée
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(requeteCreation);
        sqLiteDatabase.execSQL(createCategories);
        sqLiteDatabase.execSQL(remplissageCategorieParDefaut1);
        sqLiteDatabase.execSQL(remplissageCategorieParDefaut2);
        sqLiteDatabase.execSQL(remplissageCategorieParDefaut3);
    }

    /**
     * Fonction abstraite à surcharger
     * onIpgrade est appelée lors du changement de version de la base de donnée
     * @param sqLiteDatabase base de donnée
     * @param i ancienne version de la base de données
     * @param i1 nouvelle version de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
