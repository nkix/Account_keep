package com.example.account_keep.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class SumFragmentAdapter extends FragmentStateAdapter {

    private List<SumTabFragment> mFragmentList; //fragment of each nav
    private List<String> mTitle;    //title of navigation bar

    public SumFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
