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

public class TabFragment extends Fragment {
    private static final String TAG = "TabFragment";
    private Button btnTest;
    private TextView mTextView;
    private String mDescription;

    public TabFragment(String arg_description) {
        mDescription = arg_description;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment,container, false);
        mTextView = view.findViewById(R.id.textTab);
        mTextView.setText(mDescription);
        btnTest = (Button) view.findViewById(R.id.btnTest);

        return view;

    }
}
