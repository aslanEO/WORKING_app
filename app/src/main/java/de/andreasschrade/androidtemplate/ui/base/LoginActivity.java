package de.andreasschrade.androidtemplate.ui.base;
import android.content.Intent;
import android.util.Log;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.andreasschrade.androidtemplate.R;
import de.andreasschrade.androidtemplate.ui.quote.ListActivity;
import de.andreasschrade.androidtemplate.ui.ViewSamplesActivity;
public class LoginActivity extends BaseActivity implements View.OnClickListener{


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Firebase
    public FirebaseAnalytics mFBAnalytics;

    //Variables
    private EditText etEmail;
    private EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get User Information

        etEmail = findViewById(R.id.email);
        etPass = findViewById(R.id.password);


        final String TAG = "Testing: ";

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG,"Signed in: " + user.getUid());
                }else{
                    Log.d(TAG,"Currently Signed Out");
                }
            }
        };

        //buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.createAccount).setOnClickListener(this);


    }
    @Override
    public void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }
    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

           case R.id.email_sign_in_button:
                signUserIn();
                break;
            case R.id.createAccount:
                createUser();
                break;
        }
    }
    private boolean checkFields(){
        String email, password;

        email = etEmail.getText().toString();
        password = etPass.getText().toString();

        if(email.isEmpty()){
            etEmail.setError("Email Required");
            return false;
        }
        if(!email.contains("@")){
            etEmail.setError("Not a Valid Email Address");
            return false;
        }
        if(password.isEmpty()){
            etPass.setError("Password Required");
            return false;
        }
        if(password.length() <=5){
            etPass.setError("Password Minimum Length is 6");
            return false;
        }

        return true;
    }
    private void signUserIn(){
        if(!checkFields()){
            return;
        }
        String userEmail = etEmail.getText().toString();
        String userPass = etPass.getText().toString();


        mAuth.signInWithEmailAndPassword(userEmail,userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "User was Signed In"
                                    , Toast.LENGTH_SHORT).show();

                            openDashBoard();

                        } else{
                            Toast.makeText(LoginActivity.this, "User was not Signed In " +
                                            "Sorry!"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void createUser(){
        Intent createIntent = new Intent(this, CreateAccActivity.class);
        startActivity(createIntent);

    }

    private void openDashBoard(){
        Intent intent = new Intent(this, ViewSamplesActivity.class);
        startActivity(intent);
    }


}
