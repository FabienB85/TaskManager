package fr.Babar.taskmanager;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.Babar.taskmanager.model.Task;

public class SectionPageAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> mListFragment = new ArrayList<>();
    private final List<Task> mTaskList = new ArrayList<>();


    public SectionPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
     public void addFragment (Fragment arg_fragment, Task arg_task){
        Fragment lFragment = arg_fragment;

        mTaskList.add(arg_task);
        mListFragment.add(lFragment);
     }

    public CharSequence getPageTitle(int position){
        return mTaskList.get(position).getNom();
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mListFragment.get(position);
    }

    @Override
    public int getCount() {
        return mListFragment.size();
    }
}
