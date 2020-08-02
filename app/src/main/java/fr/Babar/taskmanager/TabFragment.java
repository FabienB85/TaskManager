package fr.Babar.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.Babar.taskmanager.model.Task;
import fr.Babar.taskmanager.outils.AccesLocalDB;

public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";
    private Button btnTest;
    private TextView mTextView;
    private String mCategorie;
    private AccesLocalDB accesLocalDB;

    public TabFragment(String arg_categorie, AccesLocalDB arg_accesLocalDB) {
        mCategorie = arg_categorie;
        accesLocalDB = arg_accesLocalDB;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment,container, false);
        mTextView = view.findViewById(R.id.textTab);
        List<Task> taskList = accesLocalDB.recupereTask (mCategorie);
        if (taskList == null){
            mTextView.setText(mCategorie);
        }
        else
        {
            mTextView.setText(taskList.get(0).getNom());
        }

        //btnTest = (Button) view.findViewById(R.id.btnTest);

        return view;

    }
}
