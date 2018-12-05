package de.andreasschrade.androidtemplate.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.andreasschrade.androidtemplate.R;
import de.andreasschrade.androidtemplate.dummy.DummyContent;
import de.andreasschrade.androidtemplate.ui.base.BaseActivity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.andreasschrade.androidtemplate.ui.base.ChangePassActivity;
import de.andreasschrade.androidtemplate.ui.base.LoginActivity;
import de.andreasschrade.androidtemplate.ui.quote.ListActivity;
import eu.amirs.JSON;

/**
 * This Activity provides several settings. Activity contains {@link PreferenceFragment} as inner class.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class SettingsActivity extends BaseActivity{
    private Button button_cp;
    private Button button_lg;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupToolbar();
        button_cp =(Button)findViewById(R.id.button_cp);
        button_cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_cp();
            }
        });
        button_lg =(Button)findViewById(R.id.button_lg);
        button_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                log_out();
            }
        });



    }

    private void log_out(){
        FirebaseAuth.getInstance().signOut();
        finishAffinity();
        Intent y =new Intent(this,LoginActivity.class);
        startActivity(y);
    }

    private void setupToolbar() {
        final ActionBar ab = getActionBarToolbar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                openDrawer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void open_cp(){
        Intent i =new Intent(SettingsActivity.this,ChangePassActivity.class);
        startActivity(i);
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return 0;
    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    public static class SettingsFragment extends PreferenceFragment {
        public SettingsFragment() {}

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_prefs);
        }
    }
}
