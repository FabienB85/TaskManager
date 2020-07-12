package fr.Babar.taskmanager;

import java.time.Duration;
import java.util.Date;

public class Task {
    /* attibuts */
    private String nom;
    private String description;
    private Duration duree;
    private Date echeance;

    /* createur */
    public Task(String arg_nom,String arg_description){
        nom = arg_nom;
        description = arg_description;
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

}
