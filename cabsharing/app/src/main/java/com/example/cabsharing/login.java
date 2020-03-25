package com.example.cabsharing;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class login extends AppCompatActivity {
    EditText vemail,vpassword;
    Button btnlogin;
    TextView btnSignup;
    FirebaseAuth mFirebaseAuth;
    GoogleSignInApi mGoogleSignInClient;
    Button gsignin;
    private final static int  RC_SIGN_IN=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        vemail=findViewById(R.id.EMAIL);
        btnSignup=findViewById(R.id.textView3);
        vpassword=findViewById(R.id.PASSWORD);
        btnlogin=findViewById(R.id.BUTTON);
        mFirebaseAuth=FirebaseAuth.getInstance();
        gsignin=findViewById(R.id.google);



        gsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }

        });




        if(mFirebaseAuth.getCurrentUser()!=null) {
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

// Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = (GoogleSignInApi) GoogleSignIn.getClient(this, gso);








        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = vemail.getText().toString();
                String password = vpassword.getText().toString();

                if (Email.isEmpty()) {
                    vemail.setError("required");


                }
                if (password.isEmpty()) {
                    vpassword.setError("required");

                }
                if (!Email.isEmpty() && !password.isEmpty()) {

                    mFirebaseAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login.this, "succesfully logged in", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(login.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(login.this, "invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( login.this, signup.class );
                startActivity(intent);
            }
        });
    }


      public void signIn() {

          Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent((GoogleApiClient) mGoogleSignInClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);


    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(login.this, "google signin failed"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

        private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mFirebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's informatio
                                Toast.makeText(login.this, "signInWithCredential:success"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(login.this, "Failed Authentication"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                                //  updateUI(null);
                            }

                            // ...
                        }
                    });
    }


}











