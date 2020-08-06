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
    private Button btnTest;
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
            //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);
        }


        btnTest = view.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRecyclerView == null){
                    /* nothing to do*/
                }else {
                    int nombreClique = 0;
                    for (int i = 0; i < taskList.size(); i++){
                        //TODO
                        if(taskList.get(i).getSelectionne()){
                            nombreClique ++;
                        }
                    }

                }
            }
        });

        return view;

    }
}
