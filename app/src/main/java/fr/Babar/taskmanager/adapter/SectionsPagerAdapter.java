package fr.Babar.taskmanager.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import fr.Babar.taskmanager.model.Task;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

   // @StringRes
    private List<Task> taskList;
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void setTaskList(List<Task> arg_taskList) {
        this.taskList = arg_taskList;
    }

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return taskList.get(position).getNom();
    }

    @Override
    public Fragment getItem(int position) {
        //return taskList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }
}
