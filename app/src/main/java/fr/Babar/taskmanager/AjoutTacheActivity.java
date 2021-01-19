package fr.Babar.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import fr.Babar.taskmanager.model.Categorie;
import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class AjoutTacheActivity extends AppCompatActivity {
    private Spinner spinnerCategorie;
    private AccesLocalDB accesLocalDB;
    private Button btnAjoutTache;
    private EditText editTextNom;
    private EditText editTextDescription;
    private EditText editTextDuree;
    private TextView editTextDateEcheance;
    private TextView editTextHeureEcheance;
    private Spinner spinnerUrgence;
    private Spinner spinnerRecurence;
    private Spinner spinnerQualificatifDuree;
    private TextView test;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_tache);

        /* on recupère l'accès à la base de données*/
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        /* Récupération de tous les éléments déclarés dans le layout */
        editTextNom = findViewById(R.id.editTextNomTache);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDateEcheance = findViewById(R.id.editTextDateEchance);
        editTextDuree = findViewById(R.id.editTextDuree);
        editTextHeureEcheance = findViewById(R.id.editTextTimeEcheance);

        //Récupération du Spinner déclaré dans le layout
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        spinnerRecurence = findViewById(R.id.spinnerRecurence);
        spinnerUrgence = findViewById(R.id.spinnerUrgence);
        spinnerQualificatifDuree = findViewById(R.id.spinnerQualificatifDuree);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        /* définition du spinner pour la durée */
        List<String> listQualificatifDuree = new ArrayList<>();
        listQualificatifDuree.add(getResources().getString(R.string.str_minute));
        listQualificatifDuree.add(getResources().getString(R.string.str_hour));
        listQualificatifDuree.add(getResources().getString(R.string.str_day));
        listQualificatifDuree.add(getResources().getString(R.string.str_week));
        listQualificatifDuree.add(getResources().getString(R.string.str_month));
        listQualificatifDuree.add(getResources().getString(R.string.str_year));
        /* On definit une présentation du spinner quand il est déroulé */
        ArrayAdapter adapterQualificatifDuree = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listQualificatifDuree);
        adapterQualificatifDuree.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQualificatifDuree.setAdapter(adapterQualificatifDuree);

        /* définition du spinner (menu déroulant pour les catégories) */

        //Création d'une liste d'élément à mettre dans le Spinner(pour l'exemple)
        List<Categorie> categories = accesLocalDB.recupereCategories();
        if (categories == null) {
            categories = new ArrayList<>();
            categories.add(new Categorie("Erreur","Erreur"));
        } else {
            /* nothing to do*/
        }

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter adapterCategorie = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);

        /* On definit une présentation du spinner quand il est déroulé
                (android.R.layout.simple_spinner_dropdown_item) */
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerCategorie.setAdapter(adapterCategorie);

        /* Création du spinner pour la récurence */
        List<String> listRecurence = new ArrayList<>();
        listRecurence.add(getResources().getString(R.string.str_recurence));
        listRecurence.add(getResources().getString(R.string.str_1heure));
        listRecurence.add(getResources().getString(R.string.str_1jour));
        listRecurence.add(getResources().getString(R.string.str_1semaine));
        listRecurence.add(getResources().getString(R.string.str_1mois));
        listRecurence.add(getResources().getString(R.string.str_1annee));

        /* On definit une présentation du spinner quand il est déroulé */
        ArrayAdapter adapterRecurence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listRecurence);
        adapterRecurence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRecurence.setAdapter(adapterRecurence);

        /* Definition du spinner pour l'urgence / gravité */
        // création de la liste
        List<String> listUrgences = new ArrayList<>();
        listUrgences.add("Normal");
        listUrgences.add("Majeur");
        listUrgences.add("Mineur");
        // ajout de l'adapteur
        ArrayAdapter adapterUrgence = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listUrgences);
        // définition de la présentation
        adapterUrgence.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //on passe l'adapteur au spinner
        spinnerUrgence.setAdapter(adapterUrgence);

        /* traitement du bouton Ajout Tache */
        btnAjoutTache = (Button) this.findViewById(R.id.btnAjoutTache);
        btnAjoutTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageToast;
                messageToast = getString(R.string.str_tache_ajoutee);
                if (editTextNom.getText().toString().equals("")){
                    messageToast = getString(R.string.str_nom_tache_vide);
                }
                else{
                    messageToast = editTextNom.getText().toString();
                    if (editTextDescription.getText().toString().equals("")){
                        messageToast = getString(R.string.str_description_tache_vide);
                    }
                    else{
                        Task taskAAjouter = new Task("Defaut", "default");
                        taskAAjouter.setNom(editTextNom.getText().toString());
                        taskAAjouter.setDescription(editTextDescription.getText().toString());
                        taskAAjouter.setCategorie(spinnerCategorie.getSelectedItem().toString());
                        taskAAjouter.setUrgence(spinnerUrgence.getSelectedItem().toString());
                        taskAAjouter.setDuree(editTextDuree.getText().toString() + ":" + spinnerQualificatifDuree.getSelectedItem().toString());
                        taskAAjouter.setEcheance(editTextDateEcheance.getText().toString(),editTextHeureEcheance.getText().toString());
                        taskAAjouter.setRecurence(spinnerRecurence.getSelectedItem().toString());
                        taskAAjouter.setUrgence(spinnerUrgence.getSelectedItem().toString());
                        accesLocalDB.ajoutTaskDansDB(taskAAjouter);

                        /* ajout dans l'agenda */
                        ContentResolver cr = getContentResolver();
                        ContentValues values = new ContentValues();
                        // pour les tests
                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(taskAAjouter.getStartYear(), taskAAjouter.getStartMonth(),taskAAjouter.getStartDay(),taskAAjouter.getStartHour(),taskAAjouter.getStartMinute());
                        long dtstart = 0;
                        dtstart = beginTime.getTimeInMillis();
                        Calendar endTime = Calendar.getInstance();
                        /* a traiter avec la duréee */
                        String compString = spinnerQualificatifDuree.getSelectedItem().toString();
                        Integer tempInt = new Integer(editTextDuree.getText().toString());
                        if (compString.equals(getResources().getString(R.string.str_minute))){
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth(),
                                    taskAAjouter.getStartDay(),
                                    taskAAjouter.getStartHour(),
                                    taskAAjouter.getStartMinute() + tempInt);
                        }
                        else if(compString.equals(getResources().getString(R.string.str_hour))){
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth(),
                                    taskAAjouter.getStartDay(),
                                    taskAAjouter.getStartHour() + tempInt,
                                    taskAAjouter.getStartMinute());
                        }else if(compString.equals(getResources().getString(R.string.str_day))){
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth(),
                                    taskAAjouter.getStartDay() + tempInt,
                                    taskAAjouter.getStartHour() ,
                                    taskAAjouter.getStartMinute());
                        }else if(compString.equals(getResources().getString(R.string.str_week))){
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth(),
                                    taskAAjouter.getStartDay() + (7*tempInt),
                                    taskAAjouter.getStartHour() ,
                                    taskAAjouter.getStartMinute());
                        }else if(compString.equals(getResources().getString(R.string.str_month))){
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth() + tempInt,
                                    taskAAjouter.getStartDay(),
                                    taskAAjouter.getStartHour() ,
                                    taskAAjouter.getStartMinute());
                        }else if(compString.equals(getResources().getString(R.string.str_year))){
                            endTime.set(taskAAjouter.getStartYear() + tempInt,
                                    taskAAjouter.getStartMonth(),
                                    taskAAjouter.getStartDay(),
                                    taskAAjouter.getStartHour(),
                                    taskAAjouter.getStartMinute());
                        }else {
                            /* default value*/
                            endTime.set(taskAAjouter.getStartYear(),
                                    taskAAjouter.getStartMonth() ,
                                    taskAAjouter.getStartDay(),
                                    taskAAjouter.getStartHour() + 1 ,
                                    taskAAjouter.getStartMinute());
                        }
                        long dtend = 0;
                        dtend = endTime.getTimeInMillis();

                        values.put(CalendarContract.Events.DTSTART, dtstart);
                        values.put(CalendarContract.Events.DTEND, dtend);
                        values.put(CalendarContract.Events.TITLE, editTextNom.getText().toString());
                        values.put(CalendarContract.Events.DESCRIPTION, editTextDescription.getText().toString());
                        values.put(CalendarContract.Events.CALENDAR_ID, 3);
                        TimeZone timeZone = TimeZone.getDefault();
                        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
                        values.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                        /* Recurrence "FREQ=WEEKLY;COUNT=10;WKST=SU" https://tools.ietf.org/html/rfc5545#section-3.8.5.3*/
                        /* FREQ=HOURLY, FREQ=DAILY, FREQ=WEEKLY, FREQ=MONTHLY, FREQ=YEARLY*/
                        String tempRecurence = spinnerRecurence.getSelectedItem().toString();

                        if (tempRecurence.equals(getResources().getString(R.string.str_1heure))){
                            values.put(CalendarContract.Events.RRULE,"FREQ=HOURLY");

                        }else if(tempRecurence.equals(getResources().getString(R.string.str_1jour))){
                            values.put(CalendarContract.Events.RRULE,"FREQ=DAILY");

                        }else if(tempRecurence.equals(getResources().getString(R.string.str_1semaine))){
                            values.put(CalendarContract.Events.RRULE,"FREQ=WEEKLY");

                        }else if(tempRecurence.equals(getResources().getString(R.string.str_1mois))){
                            values.put(CalendarContract.Events.RRULE,"FREQ=MONTHLY");

                        }else if(tempRecurence.equals(getResources().getString(R.string.str_1annee))){
                            values.put(CalendarContract.Events.RRULE,"FREQ=YEARLY");

                        }else{
                            /* nothing to do*/
                        }

                        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
                        // get the event ID that is the last element in the Uri
                        long eventID = Long.parseLong(uri.getLastPathSegment());

                        /* Rappel*/
                        values = new ContentValues();
                        values.put(CalendarContract.Reminders.MINUTES, 15);
                        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
                        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                        uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);
                        /*TODO Sauvergarder l'id de l'event dans la tache*/

                        // insert event to calendar
                        //Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);
/*                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE,  editTextNom.getText().toString())
                        .putExtra(CalendarContract.Events.DESCRIPTION,  editTextDescription.getText().toString())
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
*/                //          .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                        //         .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
                        // startActivity(intent);

                    }
                }

                // petit message pour dire que la commande est prise en compte
                Toast toast = Toast.makeText(view.getContext(), messageToast, Toast.LENGTH_SHORT);
                toast.show();
                // termine l'activity
                finish();
            }

        });

        /* traitement du date picker */

        editTextDateEcheance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view == editTextDateEcheance) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    editTextDateEcheance.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }

            }
        });
        editTextHeureEcheance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == editTextHeureEcheance) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(),
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    editTextHeureEcheance.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, true);
                    timePickerDialog.show();
                }
            }
        });
    }
}
