package com.example.account_keep.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.account_keep.DBOpenHelper;
import com.example.account_keep.R;
import com.example.account_keep.entity.Account;

public class AddActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.acc_add);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(AddActivity.this, 1);

        //Intent intent = getIntent();
        String input = getIntent().getStringExtra("date");
        if(input == null){
            System.out.println("info not correctly passed");
        }
        String[] date = input.split(",");


        /*int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int date = intent.getIntExtra("date", 0);*/

        Button button = findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txt1 = (EditText) findViewById(R.id.description_input);
                EditText txt2 = (EditText)findViewById(R.id.amount_input);

                if(TextUtils.isEmpty(txt1.getText()) || TextUtils.isEmpty(txt2.getText())){
                    //if any field empty
                }
                else{
                    //if both fields are filled
                    //store into database
                    Account acc = dbOpenHelper.addAccount(txt1.getText().toString(), String.valueOf(txt2.getText()), date[0], date[1], date[2]);
                    Intent intent = new Intent().putExtra("result", acc);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
