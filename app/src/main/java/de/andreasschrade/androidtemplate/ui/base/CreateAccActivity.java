package de.andreasschrade.androidtemplate.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.andreasschrade.androidtemplate.R;

public class CreateAccActivity extends BaseActivity implements View.OnClickListener{


    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    private FirebaseAuth mAuth;

    //Variables
    private EditText etCEmail, etCFirst, etCLast, etCAge, etCGender, etCPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        //Get User Information

        etCEmail = findViewById(R.id.email_create);
        etCFirst = findViewById(R.id.firstName);
        etCAge = findViewById(R.id.age);
        etCLast= findViewById(R.id.last_name);
        etCGender= findViewById(R.id.gender);
        etCPass = findViewById(R.id.password_create);


        final String TAG = "Testing: ";

        mAuth = FirebaseAuth.getInstance();

        //Buttons
        findViewById(R.id.generateAcc).setOnClickListener(this);


    }
    @Override
    public void onStart(){
        super.onStart();

    }
    @Override
    public void onStop(){
        super.onStop();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.generateAcc:
                createUser();
                break;
        }
    }
    private boolean checkFields(){
        String email, password;

        email = etCEmail.getText().toString();
        password = etCPass.getText().toString();

        if(email.isEmpty()){
            etCEmail.setError("Email Required");
            return false;
        }
        if(!email.contains("@")){
            etCEmail.setError("Not a Valid Email Address");
            return false;
        }
        if(password.isEmpty()){
            etCPass.setError("Password Required");
            return false;
        }
        if(password.length() <=5){
            etCPass.setError("Password Minimum Length is 6");
            return false;
        }

        return true;
    }

    private void createUser(){
        if(!checkFields()){
            return;
        }
        String userEmail = etCEmail.getText().toString();
        String userPass = etCPass.getText().toString();

        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccActivity.this, "User was Created"
                            , Toast.LENGTH_SHORT).show();

                            //Get Data for Database
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUser_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                            Map newUser = new HashMap();

                            String firstName = etCFirst.getText().toString();
                            String lastName = etCLast.getText().toString();
                            String age = etCAge.getText().toString();
                            String gender = etCGender.getText().toString();
                            String email = etCEmail.getText().toString();
                            String pass = etCPass.getText().toString();


                            newUser.put("firstName", firstName);
                            newUser.put("lastName", lastName);
                            newUser.put("age", age);
                            newUser.put("gender", gender);
                            newUser.put("Email", email);
                            newUser.put("Pass", pass);

                            //Store in Database
                            currentUser_db.setValue(newUser);


                            returnToLogin();

                        } else{
                            Toast.makeText(CreateAccActivity.this, "User was not Created " +
                                            "Sorry!"
                                    , Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void returnToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


}
