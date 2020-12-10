package com.example.freightviewer.CellDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.freightviewer.HttpRequests.JSONPlaceHolderApi;
import com.example.freightviewer.HttpRequests.RetroClient;
import com.example.freightviewer.R;
import com.example.freightviewer.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CellActivity extends AppCompatActivity {
    TextView customerLoadNmTxt, customerBrokerTxt, tripRateTxt, truckNmTxt,
            trailerNmTxt, driverTxt, emptyMilesTxt, loadedMilesTxt, totalMilesTxt, temperatureTxt;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cell);

        int loadId = (int) getIntent().getExtras().get("loadId");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Load#: " + String.valueOf(loadId));// load# od getIntent
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customerLoadNmTxt = findViewById(R.id.customerLoadText);
        customerBrokerTxt = findViewById(R.id.customerBrokerText);
        tripRateTxt = findViewById(R.id.tripRateText);
        truckNmTxt = findViewById(R.id.truckNumberText);
        trailerNmTxt = findViewById(R.id.trailerNumberText);
        driverTxt = findViewById(R.id.driverText);
        emptyMilesTxt = findViewById(R.id.emptyMilesText);
        loadedMilesTxt = findViewById(R.id.loadMilesText);
        totalMilesTxt = findViewById(R.id.totalMilesText);
        temperatureTxt = findViewById(R.id.temperatureText);

        Call<JsonObject> call = RetroClient.getApiService().getLoadByIdForDriver("Bearer " +
                Constants.userToken, "mobile/drivers/loads/" + loadId);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d("RetrofitA", "" + response.code());
                    return;
                }

                JsonObject jsonObject = response.body();
                Log.d("RetrofitA", " " + jsonObject);

                try {
                    String totalMiles = String.valueOf(Double.valueOf(jsonObject.get("emptyMiles").toString()) + Double.valueOf(jsonObject.get("loadedMiles").toString()));

                    customerLoadNmTxt.setText(jsonObject.get("customerLoad").toString().replace("\"", ""));
                    customerBrokerTxt.setText(jsonObject.getAsJsonObject("customer").get("companyName").toString().replace("\"", ""));
                    tripRateTxt.setText(jsonObject.get("tripRate").toString());
                    truckNmTxt.setText(jsonObject.getAsJsonObject("truck").get("unitNumber").toString().replace("\"", ""));
                    trailerNmTxt.setText(jsonObject.getAsJsonObject("trailer").get("unitNumber").toString().replace("\"", ""));
                    driverTxt.setText(jsonObject.getAsJsonObject("driver").get("firstName").toString().replace("\"", "") +
                            " " + jsonObject.getAsJsonObject("driver").get("lastName").toString().replace("\"", ""));
                    emptyMilesTxt.setText(jsonObject.get("emptyMiles").toString());
                    loadedMilesTxt.setText(jsonObject.get("loadedMiles").toString());
                    totalMilesTxt.setText(totalMiles);
                    temperatureTxt.setText(jsonObject.get("temperature").toString());
                    loadStops(response.body().getAsJsonObject("loadStops"));

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Retrofit C", t.getMessage());
            }
        });
    }

    private void loadStops(JsonObject rates) {
        JsonArray stopsArrayJson = (JsonArray) rates.get("loadStop");
        LinearLayout parentStopLayout = findViewById(R.id.parentStopsLayout);

        for (int i = 1; i < stopsArrayJson.size() + 1; i++) {
            final int io = i-1;
            TextView stopsTxt = new TextView(getApplicationContext());
            final Button arrivedBtn = new Button(getApplicationContext());
            arrivedBtn.setTextSize(13);
            final Button loadedBtn = new Button(getApplicationContext());
            loadedBtn.setTextSize(13);

//            button.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.MULTIPLY);

            final JsonElement stopElement = stopsArrayJson.get(i - 1);
            JsonObject stopObject = (JsonObject) stopElement;

            arrivedBtn.setText("Arrived");

            arriveBtnPressedOnStatusUpdate(arrivedBtn,io);
            loadBtnPressedOnStatusUpdate(loadedBtn,io);

            //Horizontal box
            LinearLayout horizontalViewLayout = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 0, 0, 40);
            horizontalViewLayout.setLayoutParams(layoutParams);
            horizontalViewLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalViewLayout.setBackgroundResource(R.drawable.custom_background);

            //Two Vertical boxes
            LinearLayout verticalStopsBox = new LinearLayout(getApplicationContext());
            verticalStopsBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            verticalStopsBox.setOrientation(LinearLayout.VERTICAL);
            verticalStopsBox.setGravity(View.TEXT_ALIGNMENT_CENTER);

            LinearLayout verticalStopsButtonBox = new LinearLayout(getApplicationContext());
            verticalStopsButtonBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f));
            verticalStopsButtonBox.setOrientation(LinearLayout.VERTICAL);
            verticalStopsButtonBox.setGravity(View.TEXT_ALIGNMENT_CENTER);

            //Layout break line
            ImageView breakLine = new ImageView(getApplicationContext());
            breakLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            breakLine.setBackgroundColor(Color.BLACK);


            stopsTxt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            //Get buttons status
            if (stopObject.get("status").toString().equals(String.valueOf(0))) {
                arrivedBtn.setTextColor(Color.WHITE);
                loadedBtn.setTextColor(Color.WHITE);
            } else if (stopObject.get("status").toString().equals(String.valueOf(1))) {
                arrivedBtn.setTextColor(Color.GREEN);
                arrivedBtn.setEnabled(false);
                loadedBtn.setTextColor(Color.WHITE);
            } else if (stopObject.get("status").toString().equals(String.valueOf(2))) {
                arrivedBtn.setTextColor(Color.GREEN);
                loadedBtn.setTextColor(Color.GREEN);
                arrivedBtn.setEnabled(false);
                loadedBtn.setEnabled(false);
            }

            //Get stops street
            String stopStreet = "";
            if (!stopObject.get("street").toString().equals("null")) {
                stopStreet = stopObject.get("street").toString();
            }

            if (stopObject.get("stopType").toString().replace("\"", "").equals("PICK_UP")) {
                loadedBtn.setText("Loaded");
            } else {
                loadedBtn.setText("Unloaded");
            }

            stopsTxt.setText("" + Integer.toString(i) + ". Type: " + stopObject.get("stopType").toString().replace("\"", "") + "\n" +
                    "     " + "Street: " + stopStreet + "\n" +
                    "     " + "City: " + stopObject.get("city").toString().replace("\"", "") + "\n" +
                    "     " + "State: " + stopObject.get("state").toString().replace("\"", "") + "\n" +
                    "     " + "Zip: " + stopObject.get("zip").toString().replace("\"", "") + "\n");

            parentStopLayout.addView(horizontalViewLayout);

            horizontalViewLayout.addView(verticalStopsBox);
            horizontalViewLayout.addView(verticalStopsButtonBox);

            verticalStopsBox.addView(stopsTxt);
            verticalStopsButtonBox.addView(arrivedBtn);
            verticalStopsButtonBox.addView(loadedBtn);
        }
    }

    private void loadBtnPressedOnStatusUpdate(final Button loadedBtn,final int io) {
        loadedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadedBtn.setTextColor(Color.GREEN);
                loadedBtn.setEnabled(false);

                Call<JsonObject> call = RetroClient.getApiService().getLoadByIdForDriver("Bearer " +
                        Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Log.d("RetrofitA", "" + response.code());
                            return;
                        }
                        JsonObject ratesPressed=  response.body().getAsJsonObject("loadStops");
                        JsonArray pressedArrayJson = (JsonArray) ratesPressed.get("loadStop");
                        final JsonElement presseStopElement = pressedArrayJson.get(io);

                        JsonObject stopObjectPressed = (JsonObject) presseStopElement;

                        Log.d("StopObject", "" +stopObjectPressed.get("id").toString());

                        Call<Void> pressedLoadCall = RetroClient.getApiService().updateLoadStopStatus("Bearer " +
                                Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString() + "/stop/" +
                                stopObjectPressed.get("id").toString().replace("\"", "") + "/status/" + 2);

                        pressedLoadCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (!response.isSuccessful()) {
                                    Log.d("StopSOp", "" + response.code());
                                    return;
                                }
                                Log.d("StopSOp", "" + response.code());

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("StopSOpppp", "" + t.getMessage());

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });

    }

    private void arriveBtnPressedOnStatusUpdate(final Button arrivedBtn, final int io) {
        arrivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrivedBtn.setTextColor(Color.GREEN);
                arrivedBtn.setEnabled(false);

                Call<JsonObject> call = RetroClient.getApiService().getLoadByIdForDriver("Bearer " +
                        Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString());

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if (!response.isSuccessful()) {
                            Log.d("RetrofitA", "" + response.code());
                            return;
                        }
                        JsonObject ratesPressed=  response.body().getAsJsonObject("loadStops");
                        JsonArray pressedArrayJson = (JsonArray) ratesPressed.get("loadStop");
                        JsonElement presseStopElement = pressedArrayJson.get(io);

                        JsonObject stopObjectPressed = (JsonObject) presseStopElement;

                        Log.d("StopObject", "" +stopObjectPressed.get("id").toString());
//                        mobile/drivers/loads/318/stop/e904b50b-f0a5-418f-a900-bb0f41bb30d1/status/2
                        Call<Void> pressedLoadCall = RetroClient.getApiService().updateLoadStopStatus("Bearer " +
                                Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString() + "/stop/" +
                                stopObjectPressed.get("id").toString().replace("\"", "") + "/status/" + 1);
                        pressedLoadCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}