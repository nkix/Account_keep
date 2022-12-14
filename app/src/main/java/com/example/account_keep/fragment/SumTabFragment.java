package com.example.account_keep.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.account_keep.R;

public class SumTabFragment extends Fragment {
    private TextView textView;
    private String summary;

    private final String month_des = "Cost of month : ";

    public SumTabFragment(int sum){
        this.summary = month_des + sum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        textView = view.findViewById(R.id.sum);
        textView.setText(summary);

        return view;
    }
}
