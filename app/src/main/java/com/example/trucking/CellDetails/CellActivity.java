package com.example.trucking.CellDetails;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.trucking.HttpRequests.RetroClient;
import com.example.trucking.Model.LoadModel;
import com.example.trucking.Model.StopType;
import com.example.trucking.R;
import com.example.trucking.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CellActivity extends AppCompatActivity {
    TextView customerLoadNmTxt, customerBrokerTxt, truckNmTxt,
            trailerNmTxt, driverTxt, milesTxt, temperatureTxt,driver2Txt;
    Toolbar toolbar;
    ImageView driverImage;

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
        driverImage = findViewById(R.id.driver_image);
        truckNmTxt = findViewById(R.id.truckNumberText);
        trailerNmTxt = findViewById(R.id.trailerNumberText);
        driverTxt = findViewById(R.id.driverText);
        driver2Txt=findViewById(R.id.driver2Text);
        milesTxt = findViewById(R.id.miles_empty_loaded);
        temperatureTxt = findViewById(R.id.temperatureText);

        Call<LoadModel> call = RetroClient.getApiService().getLoadByIdForDriver("Bearer " +
                Constants.userToken, "mobile/drivers/loads/" + loadId);

        call.enqueue(new Callback<LoadModel>() {
            @Override
            public void onResponse(Call<LoadModel> call, Response<LoadModel> response) {
                if (!response.isSuccessful()) {
                    Log.d("RetrofitA", "" + response.code());
                    return;
                }
                LoadModel loadModel = response.body();

                if (!isNullOrEmpty(loadModel.getCustomerLoad())) {
                    customerLoadNmTxt.setText(loadModel.getCustomerLoad());
                }
                if (!isNullOrEmpty(loadModel.getCustomer().getCompanyName())) {
                    customerBrokerTxt.setText(loadModel.getCustomer().getCompanyName());
                }
                if (!isNullOrEmpty(loadModel.getTruck().getUnitNumber())) {
                    truckNmTxt.setText(loadModel.getTruck().getUnitNumber());
                }
                if (!isNullOrEmpty(loadModel.getCustomerLoad())) {
                    trailerNmTxt.setText(loadModel.getTrailer().getUnitNumber());
                }

                if (!isNullOrEmpty(loadModel.getDriver2().getFirstName()) && !isNullOrEmpty(loadModel.getDriver().getLastName())){
                    driver2Txt.setText(loadModel.getDriver2().getFirstName() +" " +loadModel.getDriver2().getLastName());
                }

                if (!isNullOrEmpty(loadModel.getDriver().getFirstName()) && !isNullOrEmpty(loadModel.getDriver().getLastName())){
                    driverTxt.setText(loadModel.getDriver().getFirstName() + " " + loadModel.getDriver().getLastName());
                }

                if (!isNullOrEmpty(String.valueOf(loadModel.getEmptyMiles()))) {
                    milesTxt.setText(String.valueOf(loadModel.getEmptyMiles()));
                    if (!isNullOrEmpty(String.valueOf(loadModel.getLoadedMiles()))){
                        milesTxt.setText(String.valueOf(loadModel.getEmptyMiles())+"/"+String.valueOf(loadModel.getLoadedMiles()));
                    }
                }

                if (!isNullOrEmpty(String.valueOf(loadModel.getTemperature()))) {
                    temperatureTxt.setText(String.valueOf(loadModel.getTemperature()));
                }

                loadStops(loadModel);
            }

            @Override
            public void onFailure(Call<LoadModel> call, Throwable t) {
                Log.d("Retrofit C", t.getMessage());
            }
        });


    }

    private void loadStops(LoadModel loadModel) {

        for (int i = 0; i < loadModel.getLoadStops().getLoadStop().size(); i++) {
             int stopNumber = i+1;

            View stopsLayout =LayoutInflater.from(this).inflate(R.layout.stops_layout, null);


            Button arrivedBtn = stopsLayout.findViewById(R.id.arrive_btn);
            Button loadedBtn = stopsLayout.findViewById(R.id.loaded_btn);

            arrivedBtn.setText("Arrived");

            inflateStopLayout(i,stopNumber,loadModel,arrivedBtn,loadedBtn,stopsLayout);
            arriveBtnPressedOnStatusUpdate(arrivedBtn,loadModel.getLoadStops().getLoadStop().get(i).getId());
            loadBtnPressedOnStatusUpdate(loadedBtn,loadModel.getLoadStops().getLoadStop().get(i).getId());
        }
    }

    private void inflateStopLayout(int i,int stopNumber,LoadModel loadModel,Button arrivedBtn, Button loadedBtn,View stopsLayout) {
        //Define parentStopLayout
        ViewGroup parentStopLayout = findViewById(R.id.parentStopsLayout);

        TextView txtType,txtStreet,txtCity,txtState,txtZip;
        txtType = stopsLayout.findViewById(R.id.stop_type);
        txtStreet = stopsLayout.findViewById(R.id.stop_street);
        txtCity = stopsLayout.findViewById(R.id.stop_city);
        txtState = stopsLayout.findViewById(R.id.stop_state);
        txtZip = stopsLayout.findViewById(R.id.stop_zip);

        //Get buttons status
        if (loadModel.getLoadStops().getLoadStop().get(i).getStatus() == 0) {
            arrivedBtn.setTextColor(Color.WHITE);
            loadedBtn.setTextColor(Color.WHITE);
        } else if (loadModel.getLoadStops().getLoadStop().get(i).getStatus() == 1) {
            arrivedBtn.setTextColor(Color.WHITE);
            arrivedBtn.setBackgroundColor(Color.GREEN);
            arrivedBtn.setEnabled(false);
            loadedBtn.setTextColor(Color.WHITE);
        } else if (loadModel.getLoadStops().getLoadStop().get(i).getStatus() == 2) {
            arrivedBtn.setTextColor(Color.WHITE);
            loadedBtn.setTextColor(Color.WHITE);
            arrivedBtn.setBackgroundColor(Color.GREEN);
            loadedBtn.setBackgroundColor(Color.GREEN);
            arrivedBtn.setEnabled(false);
            loadedBtn.setEnabled(false);
        }

        //Get stops delivery type
        if (!isNullOrEmpty(loadModel.getLoadStops().getLoadStop().get(i).getStopType().toString())){
            txtType.setText(Integer.toString(stopNumber)+ "." + " "+loadModel.getLoadStops().getLoadStop().get(i).getStopType().getValue());
        }

        //Get stops street
        if (!isNullOrEmpty(loadModel.getLoadStops().getLoadStop().get(i).getStreet())){
            txtStreet.setText(loadModel.getLoadStops().getLoadStop().get(i).getStreet());
        }

        //Get stops city
        if (!isNullOrEmpty(loadModel.getLoadStops().getLoadStop().get(i).getCity())){
            txtCity.setText(loadModel.getLoadStops().getLoadStop().get(i).getCity());
        }

        //Get stops state
        if (!isNullOrEmpty(loadModel.getLoadStops().getLoadStop().get(i).getState().getAbbreviation())){
            txtState.setText(loadModel.getLoadStops().getLoadStop().get(i).getState().getAbbreviation());
        }

        //Get stops zip
        if (!isNullOrEmpty(loadModel.getLoadStops().getLoadStop().get(i).getZip())){
            txtZip.setText(loadModel.getLoadStops().getLoadStop().get(i).getZip());
        }

        if (loadModel.getLoadStops().getLoadStop().get(i).getStopType() == StopType.PICK_UP) {
            loadedBtn.setText("Loaded");
        } else {
            loadedBtn.setText("Unloaded");
        }

        parentStopLayout.addView(stopsLayout);
    }

    private void loadBtnPressedOnStatusUpdate(final Button loadedBtn, final String loadId) {
        loadedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CellActivity.this);
                builder.setMessage("Are you sure you want to perform this action?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                loadedBtn.setTextColor(Color.WHITE);
                                loadedBtn.setBackgroundColor(Color.GREEN);
                                loadedBtn.setEnabled(false);

                                Call<Void> pressedLoadCall = RetroClient.getApiService().updateLoadStopStatus("Bearer " +
                                        Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString() + "/stop/" +
                                        String.valueOf(loadId) + "/status/" + 2);
                                pressedLoadCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        })
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.create();
                builder.show();
            }
        });

    }

    private void arriveBtnPressedOnStatusUpdate(final Button arrivedBtn, final String loadId) {
        arrivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(CellActivity.this);
                builder.setMessage("Are you sure you want to perform this action?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                arrivedBtn.setTextColor(Color.WHITE);
                                arrivedBtn.setBackgroundColor(Color.GREEN);
                                arrivedBtn.setEnabled(false);

                                Call<Void> pressedLoadCall = RetroClient.getApiService().updateLoadStopStatus("Bearer " +
                                        Constants.userToken, "mobile/drivers/loads/" + getIntent().getExtras().get("loadId").toString() + "/stop/" +
                                        loadId + "/status/" + 1);
                                pressedLoadCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Log.d("ButtonFailed"," "+response.code());

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Log.d("ButtonFailed",t.getMessage());
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                 builder.create();
                 builder.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean isNullOrEmpty(String str) {
        if (str != null || !TextUtils.isEmpty(str))
            return false;
        return true;
    }
}