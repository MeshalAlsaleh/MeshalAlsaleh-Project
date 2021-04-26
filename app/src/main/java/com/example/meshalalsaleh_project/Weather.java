package com.example.meshalalsaleh_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather extends AppCompatActivity {

    //String weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q=riyadh&appid=89e50054f450be0839111da177e3c076&units=metric";
    ImageView weatherBackground;
    TextView temperature, hum;
    JSONObject jsonObj;
    String Scity;


    int resource;

    int weather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        temperature = (TextView) findViewById(R.id.temperature);
        hum = (TextView) findViewById(R.id.hum);
        weatherBackground = (ImageView) findViewById(R.id.weatherbackground);
        Button btnCity = (Button)findViewById(R.id.btnCity);
        EditText city = (EditText)findViewById(R.id.city);
        Button btnBack = (Button)findViewById(R.id.btnBack);


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scity=city.getText().toString();
                String url="http://api.openweathermap.org/data/2.5/weather?q="+Scity+"&appid=89e50054f450be0839111da177e3c076&units=metric";
                weather(url);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Weather.this, MainActivity.class));
            }
        });



    }

    public void weather(String url){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Meshal", response.toString());

                try {
                    JSONObject jsonMain = response.getJSONObject("main");
                    Log.d("Meshal","subObject"+jsonMain.toString());

                    double temp = jsonMain.getDouble("temp");
                    Log.d("Meshal", "temp"+ String.valueOf(temp));
                    temperature.setText(String.valueOf(temp));

                    double humidity=jsonMain.getDouble("humidity");
                    hum.setText(String.valueOf(humidity));

                    JSONArray weatherArray = response.getJSONArray("weather");
                    Log.d("Meshal", "Bulk-Array:"+ weatherArray.toString());
                    weatherPic(weatherArray);

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Meshal", "Error in url");

            }
        });
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(jsonObj);
    }

    public void weatherPic(JSONArray jsonArray){
        for (int i=0; i < jsonArray.length(); i++){
            try {
                JSONObject oneObject = jsonArray.getJSONObject(i);
                Log.d("Meshal", "jsonArray(i): " + oneObject.toString());

                String weatherConditions = oneObject.getString("main");
                Log.d("Meshal", "weather conitions: "+ weatherConditions);

                if (weatherConditions.equals("Clear")){
                    Log.d("Meshal", "Looking for clear sky image");

                    weatherBackground.setImageResource(R.drawable.clear);
                    resource=R.drawable.clear;


                }

                if (weatherConditions.equals("Clouds")){
                    Log.d("Meshal", "Looking for cloudy sky image");

                    weatherBackground.setImageResource(R.drawable.cloudy);
                    resource=R.drawable.cloudy;


                }

                if (weatherConditions.equals("Rainy")){
                    Log.d("Meshal", "Looking for rainy sky image");

                    weatherBackground.setImageResource(R.drawable.rain);
                    resource=R.drawable.rain;
                }
            }

            catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public int getResurce(){

        return resource;

    }

}