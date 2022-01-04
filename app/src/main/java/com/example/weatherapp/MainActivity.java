package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_CityID,btn_getWeatherById,btn_getWeatherByName;
    EditText dataInput;
    ListView weatherReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_CityID = findViewById(R.id.btn_GetCityID);
        btn_getWeatherById = findViewById(R.id.btn_GetWeatherByCityID);
        btn_getWeatherByName = findViewById(R.id.btn_GetWeatherByCityName);
        dataInput = findViewById(R.id.ed_dataInput);
        weatherReports = findViewById(R.id.lv_weatherReports);
        WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);

        btn_CityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               weatherDataService.getCityID(dataInput.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                   @Override
                   public void onError(String message) {
                       Toast.makeText(MainActivity.this, "something wrong", Toast.LENGTH_SHORT).show();
                   }
                   @Override
                   public void onResponse(String cityID) {
                       Toast.makeText(MainActivity.this, "city id is :" + cityID, Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });
        btn_getWeatherById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByID(dataInput.getText().toString(), new WeatherDataService.ForecastByIDResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "didn't work", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        // layout adapter
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        weatherReports.setAdapter(arrayAdapter);

                    }
                });
            }
        });
        btn_getWeatherByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherDataService.getCityForecastByName(dataInput.getText().toString(), new WeatherDataService.ForecastByNameResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "didn't work", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherReportModel> weatherReportModels) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, weatherReportModels);
                        weatherReports.setAdapter(arrayAdapter);
                    }
                });
            }
        });
    }
}