package fr.Babar.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";
    private Button btnSupprTache;
    private RecyclerView mRecyclerView;
    private String mCategorie;
    private AccesLocalDB accesLocalDB;
    private List<Task> taskList;

    public TabFragment(String arg_categorie, AccesLocalDB arg_accesLocalDB) {
        mCategorie = arg_categorie;
        accesLocalDB = arg_accesLocalDB;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment,container, false);
        create(view);

        btnSupprTache = view.findViewById(R.id.btnSupprTache);
        btnSupprTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRecyclerView == null){
                    /* nothing to do*/
                }else {

                    for (int i = 0; i < taskList.size(); i++){
                        if(taskList.get(i).getSelectionne()){
                            taskList.get(i).setSelectionne(false);
                            accesLocalDB.supprimeTask(taskList.get(i));
                            taskList.remove(i);
                            mRecyclerView.getAdapter().notifyDataSetChanged();
                        }
                    }

                }

            }
        });
        return view;

    }
    private void create (View view){
        taskList = accesLocalDB.recupereTask (mCategorie);
        if (taskList == null){
            /* nothing to do */
        }
        else
        {
            mRecyclerView = view.findViewById(R.id.RVListTache);
            // specify an adapter (see also next example)
            RecyclerViewAdapterTask mAdapter = new RecyclerViewAdapterTask(taskList);
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
