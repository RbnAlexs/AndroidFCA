package com.example.mktv.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private  static final String TAG = "openweatherimagen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imagenTemperatura;
        imagenTemperatura = (ImageView) findViewById(R.id.imagenTemperatura);
        final TextView textoTemperatura;
        textoTemperatura = (TextView) findViewById(R.id.textoTemperatura);

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://api.openweathermap.org/data/2.5/weather?id=5313135&appid=7da43a27132645c98bca16b0c26e3369&units=metric";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        try {
                            if(response.has("weather")){
                                JSONArray weatherArray = response.getJSONArray("weather");
                                JSONObject weather = weatherArray.getJSONObject(0);
                                if(weather.has("icon")){
                                    String icon = weather.getString("icon");
                                    int identificador = getResources().getIdentifier("imagen_" + icon, "drawable", getPackageName());
                                    imagenTemperatura.setImageDrawable(getResources().getDrawable(identificador, null));
                                }
                              }
                            if(response.has("main")){
                                JSONObject main = response.getJSONObject("main");
                                Double temp = main.getDouble("temp");
                                textoTemperatura.setText(("Temperatura actual: " + temp + "\u00b0"));
                            }

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error occurred ", error);
                    }
                });
        queue.add(jsonObjectRequest);
    }
}


