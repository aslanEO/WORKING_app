package de.andreasschrade.androidtemplate.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.andreasschrade.androidtemplate.ui.base.LoginActivity;
import de.andreasschrade.androidtemplate.ui.quote.ListActivity;
import eu.amirs.JSON;

/**
 * Activity demonstrates some GUI functionalities from the Android support library.
 *
 * Created by Andreas Schrade on 14.12.2015.
 */
public class ViewSamplesActivity extends BaseActivity {


    EditText foodText;
    EditText foodCalories;
    EditText foodProtein;
    EditText foodFat;
    EditText mealsCount;

    static final String API_KEY = "&_app_key=2d007bdd61b23ee675b089feef450618&q=";
    static final String API_URL = "http://api.yummly.com/v1/api/recipes?_app_id=368998cf";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samples);
        ButterKnife.bind(this);
        setupToolbar();
        foodText = findViewById(R.id.input1);
        foodCalories = findViewById(R.id.input2);
        foodProtein = findViewById(R.id.input3);
        foodFat = findViewById(R.id.input4);
        mealsCount = findViewById(R.id.input5);
    }

    @OnClick(R.id.fab)
    public void onFabClicked(View view) {
        new RetrieveFeedTask().execute();
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

    @Override
    protected int getSelfNavDrawerItem() {
        return R.id.nav_samples;

    }

    @Override
    public boolean providesActivityToolbar() {
        return true;
    }

    class  RetrieveFeedTask extends AsyncTask<Void, Void, String> {
        private Exception exception;

        protected String doInBackground(Void... urls) {
            String foodChoice = foodText.getText().toString();
            String calories = foodCalories.getText().toString();
            String protein = foodProtein.getText().toString();
            String fat = foodFat.getText().toString();
            String meals = mealsCount.getText().toString();


            try {
                URL url = new URL(API_URL + API_KEY + foodChoice + "&maxResult="+meals+"&start=0&nutrition.ENERC_KCAL.max="+calories+"&nutrition.PROCNT.max="+protein+"&nutrition.FAT.max="+fat);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();

                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }

        }


        protected void onPostExecute(String response) {
            if (response == null) {
                    response = "THERE WAS AN ERROR";
            }
            JSON products = new JSON(response).key("matches");
            String resultText = "";
            for (int i = 0; i < products.count(); i++) {
                JSON productInfo = products.index(i);
                String ingredients = "   INGREDIENTS:\n ";
                for (int j = 0; j < productInfo.key("ingredients").count(); j++) {
                    if(j == 0){
                        ingredients += "     •";
                    }else{
                        ingredients += "\n      •";
                    }
                    ingredients += productInfo.key("ingredients").index(j).stringValue();
                }

                resultText += String.format("\n\n %s \n %s  \n\n  %s",

                        (i+1)+") "+productInfo.key("sourceDisplayName").stringValue(),
                        productInfo.key("id").stringValue(),
                        ingredients

                );
            }

            String foodChoice = foodText.getText().toString();
            DummyContent.DummyItem a = new DummyContent.DummyItem("1", R.drawable.p7, foodChoice, "", resultText);
            DummyContent inst = new DummyContent();
            inst.addItem(a);
            openDashBoard();
        }


        public Drawable LoadImageFromWebOperations(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e){
                return null;
            }
        }


    }
    private void openDashBoard(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
