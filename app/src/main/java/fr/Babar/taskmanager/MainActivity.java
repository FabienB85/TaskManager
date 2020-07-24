package fr.Babar.taskmanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* label de test*/
       // leLabel = findViewById(R.id.textView2);

        /* Liste de tache  pour faire les onglets */

        /* init Database */
        accesLocalDB = new AccesLocalDB(this.getApplicationContext());

        taskList.add(new Task("Tache 1", "Faire la tache 1"));
        taskList.add(new Task("Tache 2", "Faire la tache 2"));
        taskList.add(new Task("Tache 3", "Faire la tache 3"));

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager(), 0);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager(mViewPager);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void setUpViewPager (ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager(),0);
        for (int i = 0; i < taskList.size(); i++){
            adapter.addFragment(new TabFragment(taskList.get(i).getDescription()),taskList.get(i));
        }
        viewPager.setAdapter(adapter);
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
           // leLabel.setText("Ajouter");
            return true;
        }else if (id == R.id.MenuEnvoyer) {
           // leLabel.setText("Envoyer");
            return true;
        }else{
           // leLabel.setText("Les autres items");
            return true;
        }


        //return super.onOptionsItemSelected(item);
    }
}