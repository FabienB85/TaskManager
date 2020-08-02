package fr.Babar.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class MainActivity extends AppCompatActivity {

    //private TextView leLabel;
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;
    private List<Task> taskList = new ArrayList<>();
    private AccesLocalDB accesLocalDB ;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"; // TODO A virer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* init Database */
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(mViewPager);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    public void setUpViewPager (ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager(),0);
        List<String> categories = accesLocalDB.recupereCategories();
        if (categories != null) {
            for (int i = 0; i < categories.size(); i++) {
                adapter.addFragment(new TabFragment(getString(R.string.str_defaut)), categories.get(i));
            }
            viewPager.setAdapter(adapter);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.MenuAjouter) {
            ajouterTache();
            return true;
        }else if (id == R.id.MenuEnvoyer) {
           // leLabel.setText("Envoyer");
            return true;
        }else{
           // leLabel.setText("Les autres items");
            return true;
        }
    }

    /**
     * La fonction est appelée lorsque l'utilisateur appuie sur le bouton ajouter
     * du menu. Cela créé une nouvelle activité
     */
    public void ajouterTache() {
        Intent intent = new Intent(this, AjoutTacheActivity.class);
        String message = "Ajout d'un tâche";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}