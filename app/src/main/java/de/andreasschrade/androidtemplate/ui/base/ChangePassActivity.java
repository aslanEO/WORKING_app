package de.andreasschrade.androidtemplate.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.andreasschrade.androidtemplate.R;
import de.andreasschrade.androidtemplate.ui.SettingsActivity;
import de.andreasschrade.androidtemplate.ui.quote.ListActivity;

public class ChangePassActivity extends AppCompatActivity {

    EditText ip;
    FirebaseAuth auth;
    private Button button_sav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        ip=(EditText)findViewById(R.id.new_p);
        button_sav =(Button)findViewById(R.id.button_sr);
        button_sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changep(view);
                finish();
                back_main();
            }
        });
    }
    public void changep(View v){
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.updatePassword(ip.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ChangePassActivity.this, "Change successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ChangePassActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });



}}
    public void back_main(){
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }
}
