package fr.Babar.taskmanager.model;

import android.widget.CheckBox;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Task {
    /* attibuts */
    private Integer id;
    private String nom;
    private String description;
    private String duree;
    private String echeance;
    private String categorie;
    private String recurence;
    private String urgence;
    private List<String> invite;
    /* pour indiquer si une tâche est sélectionnée dans une liste */
    private Boolean selectionne;
    private CheckBox checkBox;
    private int startYear;
    private int startMonth;
    private int startDay;
    private int startHour;
    private int startMinute;



    /* createur */
    public Task(String arg_nom,String arg_description){
        nom = arg_nom;
        description = arg_description;
        selectionne = false;

    }
    public Task(){
        selectionne = false;
    }

    /* getter / setter */
    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getDuree() {
        return duree;
    }

    public String getEcheance() {
        return echeance;
    }
    public void setNom(String arg_nom) {
        this.nom = arg_nom;
    }

    public void setDescription(String arg_description) {
        this.description = arg_description;
    }

    public void setDuree(String arg_duree) {
        this.duree = arg_duree;
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

    public String getRecurence() {
        return recurence;
    }

    public void setRecurence(String recurence) {
        this.recurence = recurence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEcheance(String arg_date, String arg_time) {
        final String SEPARATEURDATE = "-";
        final String SEPARATEURTIME = ":";
        String[] element_date = arg_date.split(SEPARATEURDATE);
        String[] element_time = arg_time.split(SEPARATEURTIME);
        echeance = element_date[2]; // annee
        startYear = Integer.valueOf(element_date[2]);
        // pour être sur que les valeurs soient sur 2 digits
        if (Integer.valueOf(element_date[1]) < 10){
            echeance = echeance + "0";
        }
        echeance = echeance + element_date[1]; // mois
        startMonth = Integer.valueOf(element_date[1]) - 1 ;/* pour l'agenda le mois commence à 0*/
        if (Integer.valueOf(element_date[0]) < 10){
            echeance = echeance + "0";

        }
        echeance = echeance + element_date[0]; // jour
        startDay = Integer.valueOf(element_date[0]);

        if (Integer.valueOf(element_time[0]) < 10){
            echeance = echeance + "0";

        }
        echeance = echeance + element_time[0]; // heure
        startHour = Integer.valueOf(element_time[0]);
        if (Integer.valueOf(element_time[1]) < 10){
            echeance = echeance + "0";

        }
        echeance = echeance +element_time[1]; // minute
        startMinute = Integer.valueOf(element_time[1]);
    }

    public void setEcheance(String arg_echeance) {
        echeance = arg_echeance;

    }
    public String getEcheanceFormattee(){
        String echeanceFormattee = echeance.substring(0,4) + "-" + echeance.substring(4,6)
                + "-" + echeance.substring(6,8) + " " + echeance.substring(8,10)
                + "H" +echeance.substring(10);
        return echeanceFormattee;
    }

    public String getDateEcheance(){
        String dateEcheance = echeance.substring(6,8) + "-" + echeance.substring(4,6)
                + "-" + echeance.substring(0,4);
        return dateEcheance;
    }
    public String getTimeEcheance(){
        String timeEcheance = echeance.substring(8,10) + ":" +echeance.substring(10);
        return timeEcheance;
    }

    public Boolean getSelectionne() {
        return selectionne;
    }

    public void setSelectionne(Boolean arg_selectionne) {

        this.selectionne = arg_selectionne;
        this.checkBox.setChecked(arg_selectionne);
    }

    public void setSelectionne(boolean arg_selectionne, CheckBox arg_checkBoxTache) {
        this.selectionne = arg_selectionne;
        checkBox = arg_checkBoxTache;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }
}
