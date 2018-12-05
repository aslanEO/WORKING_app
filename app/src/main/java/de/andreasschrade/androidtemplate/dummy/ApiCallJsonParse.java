package de.andreasschrade.androidtemplate.dummy;

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

import eu.amirs.JSON;

//public class ApiCallJsonParse {
//
//
//
//
//
//    EditText foodText;
//    TextView responseView;
//
//    static final String API_KEY = "&_app_key=2d007bdd61b23ee675b089feef450618&q=";
//    static final String API_URL = "http://api.yummly.com/v1/api/recipes?_app_id=368998cf";
//    static final String API_URL = "http://api.yummly.com/v1/api/recipes?_app_id=368998cf&_app_key=2d007bdd61b23ee675b089feef450618&q=";
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////
////
////        responseView = findViewById(R.id.responseView);
////        namey = findViewById(R.id.namey);
////        foodText = findViewById(R.id.foodText);
////        responseView.setMovementMethod(new ScrollingMovementMethod());
////
////        Button queryButton = findViewById(R.id.queryButton);
////        queryButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                new RetrieveFeedTask().execute();
////            }
////        });
//
//        class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
//
//            private Exception exception;
//
//            protected void onPreExecute() {
//                responseView.setText("Requesting");
//
//            }
//
//            protected String doInBackground(Void... urls) {
//                String foodChoice = foodText.getText().toString();
//
//                try {
//                    URL url = new URL(API_URL + API_KEY + foodChoice + "&maxResult=3&start=0");
//                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                    try {
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                        StringBuilder stringBuilder = new StringBuilder();
//                        String line;
//                        while ((line = bufferedReader.readLine()) != null) {
//                            stringBuilder.append(line).append("\n");
//                        }
//                        bufferedReader.close();
//                        return stringBuilder.toString();
//                    } finally {
//                        urlConnection.disconnect();
//
//                    }
//                } catch (Exception e) {
//                    Log.e("ERROR", e.getMessage(), e);
//                    return null;
//                }
//
//            }
//
//
//            protected void onPostExecute(String response) {
//                if (response == null) {
//                    response = "THERE WAS AN ERROR";
//                }
//
//                JSON products = new JSON(response).key("matches");
//                String resultText = "";
//                resultText += String.valueOf(products.count());
//                for (int i = 0; i < products.count(); i++) {
//                    JSON productInfo = products.index(i);
////                String arr = productInfo.key("ingredients").stringValue();
////                resultText += arr;
//
//                    resultText += String.format("\n\n %s \n %s  \n  %s",
//
//                            productInfo.key("sourceDisplayName").stringValue(),
//                            productInfo.key("id").stringValue(),
//                            productInfo.key("ingredients").stringValue()
//
//                    );
//                }
//
//                //namey.setText(resultText);
//                responseView.setText(resultText);
//
//            }
//        }
