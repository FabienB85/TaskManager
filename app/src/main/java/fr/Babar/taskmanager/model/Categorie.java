package fr.Babar.taskmanager.model;

import java.util.List;

public class Categorie {
    /* attibuts */
    private String nom;
    private String description;
    private Boolean selectionne;


    /* createur */
    public Categorie(String arg_nom, String arg_description){
        nom = arg_nom;
        description = arg_description;
        selectionne = false;

    }
    public Categorie(){
        //TODO completer les structures par défaut
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

    public void setSelectionne(Boolean arg_selectionne) {
        this.selectionne = arg_selectionne;
    }
}
