package fr.Babar.taskmanager.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private String requeteCreation = "create table taches("
            + "nom TEXT PRIMARY KEY,"
            + "description TEXT NOT NULL,"
            + "duree TEXT NOT NULL,"
            + "echeance TEXT NOT NULL,"
            +"categorie TEXT NOT NULL);";
    /**
     * Constructeur de la classe
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * Fonction abstraite à surcharger
     * oncreate est appelée lors de la création de la base de donnée
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(requeteCreation);
    }

    /**
     * Fonction abstraite à surcharger
     * onIpgrade est appelée lors du changement de version de la base de donnée
     * @param sqLiteDatabase
     * @param i ancienne version de la base de données
     * @param i1 nouvelle version de la base de données
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
