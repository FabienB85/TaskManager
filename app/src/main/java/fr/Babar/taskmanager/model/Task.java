package fr.Babar.taskmanager.model;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Task {
    /* attibuts */
    private Integer id;
    private String nom;
    private String description;
    private Duration duree;
    private Date echeance;
    private String categorie;
    // private Date recurence; //TODO Traiter la récurence
    private String urgence;
    private List<String> invite;


    /* createur */
    public Task(String arg_nom,String arg_description){
        nom = arg_nom;
        description = arg_description;
    }
    public Task(){
        //TODO completer les structures par défaut
    }

    /* getter / setter */
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Duration getDuree() {
        return duree;
    }

    public Date getEcheance() {
        return echeance;
    }
    public void setNom(String arg_nom) {
        this.nom = arg_nom;
    }

    public void setDescription(String arg_description) {
        this.description = arg_description;
    }

    public void setDuree(Duration arg_duree) {
        this.duree = arg_duree;
    }

    public void setEcheance(Date arg_echeance) {
        this.echeance = arg_echeance;
    }

    public String getCategorie() {
        return categorie;
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getUrgence() {
        return urgence;
    }

    public void setUrgence(String urgence) {
        this.urgence = urgence;
    }
    public List<String> getInvite() {
        return invite;
    }

    public void setInvite(List<String> invite) {
        this.invite = invite;
    }
}
