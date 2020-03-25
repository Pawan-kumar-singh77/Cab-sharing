package com.example.cabsharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    EditText emaild,Password,cPassword;
    Button btnsignup;
    TextView btnlogin;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emaild=findViewById(R.id.EMAIL);
        cPassword=findViewById(R.id.editText2);
        btnlogin=findViewById(R.id.textView3);
        Password=findViewById(R.id.PASSWORD);
        btnsignup=findViewById(R.id.BUTTON);
        if(mFirebaseAuth.getCurrentUser()!=null)
        {
            Intent intent=new Intent(signup.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emaild.getText().toString();
                String password = Password.getText().toString();
                String cpassword = cPassword.getText().toString();

                if (Email.isEmpty()) {
                    emaild.setError("required");


                }
                if (password.isEmpty()) {
                    Password.setError("required");

                }
                if(password.length()<8)
                {
                    Password.setError("required");
                }
                if (cpassword.isEmpty()) {
                    cPassword.setError("required");

                }
                if (!Email.isEmpty() && !password.isEmpty() && !cpassword.isEmpty()) {
                    if (cpassword.equals(password)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(signup.this, "created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(signup.this, MainActivity.class);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(signup.this, "not created", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(signup.this, "password and confirm password should be same", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( signup.this, login.class );
                startActivity(intent);
            }
        });
    }

}









