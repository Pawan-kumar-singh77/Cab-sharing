package com.example.vipingooglesignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class dassboard extends AppCompatActivity {


    Button singout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dassboard);
        singout=findViewById(R.id.button);

        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dassboard.this,MainActivity.class));
            }
        });




    }
}
