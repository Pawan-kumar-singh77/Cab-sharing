package com.example.cabsharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void LOGOUT(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
        finish();
    }
}
