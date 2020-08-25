package fr.Babar.taskmanager.model;

import android.widget.CheckBox;

import java.util.List;

public class Categorie {
    /* attibuts */
    private String nom;
    private String description;
    private Boolean selectionne;
    private CheckBox checkBox;


    /* createur */
    public Categorie(String arg_nom, String arg_description){
        nom = arg_nom;
        description = arg_description;
        selectionne = false;

    }
    public Categorie(){
        selectionne = false;
    }

    /* getter / setter */
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public void setNom(String arg_nom) {
        this.nom = arg_nom;
    }

    public void setDescription(String arg_description) {
        this.description = arg_description;
    }

    public Boolean getSelectionne() {
        return selectionne;
    }

    public void setSelectionne(Boolean arg_selectionne, CheckBox arg_checkBox) {

        this.selectionne = arg_selectionne;
        this.checkBox = arg_checkBox;
    }
    public void setSelectionne(Boolean arg_selectionne) {
        this.selectionne = arg_selectionne;
        this.checkBox.setChecked(arg_selectionne);
    }

    public String toString(){
        return nom;
    }
}
