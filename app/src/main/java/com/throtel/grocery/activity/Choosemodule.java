package com.throtel.grocery.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.throtel.grocery.R;

import books.activity.LoginbookActivity;

public class Choosemodule extends BaseActivity {
LinearLayout books,grocery,test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemodule);
        books=findViewById(R.id.books);
        grocery=findViewById(R.id.grocery);
        test=findViewById(R.id.test);

        grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choosemodule.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choosemodule.this, LoginbookActivity.class);
                startActivity(intent);



            }
        });
    }
}