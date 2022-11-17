package com.example.account_keep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.account_keep.entity.Account;
import com.example.account_keep.DBOpenHelper;
import com.example.account_keep.R;

public class EditActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acc_edit);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(EditActivity.this, 1);

        EditText desEdit = findViewById(R.id.description_input);
        EditText amountEdit = findViewById(R.id.amount_input);

        Intent intent = getIntent();
        Account acc = intent.getParcelableExtra("acc");

        desEdit.setText(acc.getDescription());
        amountEdit.setText(String.valueOf(acc.getAmount()));

        Button button = findViewById(R.id.button_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(desEdit.getText()) || TextUtils.isEmpty(amountEdit.getText())){
                    //if any field empty

                }
                else{
                    //if both fields are filled
                    //store into database
                    Account new_acc = new Account();
                    new_acc.setId(acc.getId());
                    new_acc.setDescription(desEdit.getText().toString());
                    new_acc.setAmount(Float.parseFloat(amountEdit.getText().toString()));
                    dbOpenHelper.dataUpdate(new_acc);

                    finish();
                }
            }
        });

    }

}
