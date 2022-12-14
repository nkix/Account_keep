package com.example.account_keep.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.example.account_keep.ItemAnimator;
import com.example.account_keep.SpacesItemDecoration;
import com.example.account_keep.adapter.ListEditAdapter;
import com.example.account_keep.entity.Account;
import com.example.account_keep.DBOpenHelper;
import com.example.account_keep.R;
import com.example.account_keep.listener.ItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int year, month, date;
    private GestureDetector gestureDetector;
    private DBOpenHelper dbOpenHelper;
    private final List<Account> acList = new ArrayList<Account>();
    boolean newAccountAdd = false;
    boolean isPause = false;
    ActivityResultLauncher<String> addResultLauncher;
    ActivityResultLauncher<Account> editResultLauncher;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //set database
        dbOpenHelper = new DBOpenHelper(MainActivity.this, 1);

        //set recyclerView for showing the list of accounts
        ListEditAdapter recyclerAdapter = new ListEditAdapter(acList);
        RecyclerView recyclerView = findViewById(R.id.acc_list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(10, 0, 0));
        recyclerView.setItemAnimator(new ItemAnimator());

        //set OnItemClickListener for recyclerView
        //handle onclick and onlongclick
        recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView, new ItemClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Context context = view.getContext();
                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("acc", ListEditAdapter.getItem(position));
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                ImageButton delete = view.findViewById(R.id.deleteButton);

                if(delete.getVisibility() == View.INVISIBLE){
                    delete.setVisibility(View.VISIBLE);
                }
                else{
                    delete.setVisibility(View.INVISIBLE);
                }
            }
        }));

        //deal with result returned by AddActivity
        //add the result (new account) into acList
        addResultLauncher = registerForActivityResult(new AddResultContract(), result -> {
            if(result != null){
                recyclerAdapter.addItem(recyclerAdapter.getItemCount(), result);
                newAccountAdd = true;
            }
        });

        //deal with result returned by EditActivity
        //try to refresh the list after edit page finished
        editResultLauncher = registerForActivityResult(new EditResultContract(), result -> {
            if(result != null){

            }
        });


        //set calendarView
        CalendarView calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener((calendarView1, year, month, date) -> {

            //Save date that user set
            //getDate() since only return current time
            MainActivity.this.year = year;
            MainActivity.this.month = month;
            MainActivity.this.date = date;

            //get accounts of this date from db
            //dynamically put accounts into linearlayout
            recyclerAdapter.setAccList(dbOpenHelper.searchAccount(year, month, date));

            /*for(Account acc: acList){
                System.out.println(acc.getDescription() + "," + acc.getAmount());
            }*/

            //use floatingActionButton to implement add function
            FloatingActionButton button = findViewById(R.id.floatingActionButton);
            button.setClickable(true);
            button.setOnClickListener(view -> {
                isPause = true;
                String input = year + "," + month + "," + date;
                addResultLauncher.launch(input);
            });
        });
    }

    /**
     * transfer added account from add view to main activity
     */
    class AddResultContract extends ActivityResultContract<String, Account>{

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, String s) {
            return new Intent(context, AddActivity.class).putExtra("date", s);
        }

        @Override
        public Account parseResult(int resultCode, @Nullable Intent intent) {
            if(resultCode == RESULT_OK && intent != null){
                return intent.getParcelableExtra("result");
            }
            return null;
        }
    }

    /**
     * transfer edited account from edit view to main activity
     */
    class EditResultContract extends ActivityResultContract<Account, Account>{

        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Account account) {
            return new Intent(context, EditActivity.class).putExtra("account", account);
        }

        @Override
        public Account parseResult(int resultCode, @Nullable Intent intent) {
            if(resultCode == RESULT_OK && intent != null){
                return intent.getParcelableExtra("result");
            }
            return null;
        }
    }

}