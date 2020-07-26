package fr.Babar.taskmanager;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionPageAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> mListFragment = new ArrayList<>();
    private final List<String> mTitreList = new ArrayList<>();


    public SectionPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }
     public void addFragment (Fragment arg_fragment, String arg_titre){
        mTitreList.add(arg_titre);
        mListFragment.add(arg_fragment);
     }

    public CharSequence getPageTitle(int position){
        return mTitreList.get(position);
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
