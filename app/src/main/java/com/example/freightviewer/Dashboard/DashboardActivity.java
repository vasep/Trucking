package com.example.freightviewer.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.freightviewer.HttpRequests.JSONPlaceHolderApi;
import com.example.freightviewer.Login.MainLoginActivity;
import com.example.freightviewer.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yuyang.stickyheaders.StickyLinearLayoutManager;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DashboardRecyclerAdapter dashboardRecyclerAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://truckingnew-env.eba-q2gns4ca.us-east-1.elasticbeanstalk.com/api/v1/trucking/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JSONPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JSONPlaceHolderApi.class);
//        Call<JsonObject> call = jsonPlaceHolderApi.getLoadsForDriverCompletedLoads("Bearer " +
//                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzb2ZlciIsInJvbGVzIjoiUk9MRV9EUklWRVIiLCJpYXQiOjE2MDY2OTk5OTd9.aFw-QVazlbbfeaQUR2JmmhdnxwYax2lLppOiyY6jNjA");
//        //Enqueue to call on the back thread
//        call.enqueue(new Callback<JsonObject>() {
//            @Override
//            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                if(!response.isSuccessful()){
//                    Log.d("RetrofitA",""+response.code());
//                    return;
//                }
//
//                JsonArray jsonArrayObject = response.body().getAsJsonArray("genericModel");
//                ArrayList<Integer> loadsId = new ArrayList<>();
//
//                for (int i = 0; i < jsonArrayObject.size(); i++) {
//
//                    JsonElement idElement = jsonArrayObject.get(i);
//                    JsonObject idElementObject = (JsonObject) idElement;
//                    int id = Integer.valueOf(idElementObject.get("id").toString());
//                    loadsId.add(id);
//                }
//                Log.d("RetrofitA",""+loadsId);
//
////                Load load = response.body();
////                Log.d("RetrofitB","Count: " + load.getCount() + "\n" + "Generic Model: " + load.getGenericModel());
////                List<String> arej = load.getGenericModel();
////                int i = arej.size();
//
//            }
//
//            @Override
//            public void onFailure(Call<JsonObject> call, Throwable t) {
//                Log.d("Retrofit C",t.getMessage());
//            }
//        });

        recyclerView = findViewById(R.id.dashboard_recycler);
//        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(getList(), getApplicationContext());
        StickyLinearLayoutManager stickyLinearLayoutManager = new StickyLinearLayoutManager(this, dashboardRecyclerAdapter) {
            @Override
            public boolean isAutoMeasureEnabled() {
                return true;
            }

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                RecyclerView.SmoothScroller smoothScroller = new TopSmoothScroller(recyclerView.getContext());
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

            class TopSmoothScroller extends LinearSmoothScroller {

                TopSmoothScroller(Context context) {
                    super(context);
                }

                @Override
                public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                    return boxStart - viewStart;
                }
            }

        };
        stickyLinearLayoutManager.elevateHeaders(5);
        recyclerView.setLayoutManager(stickyLinearLayoutManager);
        recyclerView.setAdapter(dashboardRecyclerAdapter);
        stickyLinearLayoutManager.setStickyHeaderListener(new StickyLinearLayoutManager.StickyHeaderListener() {
            @Override
            public void headerAttached(View headerView, int adapterPosition) {
                Log.d("StickyHeader", "Header Attached : " + adapterPosition);
            }

            @Override
            public void headerDetached(View headerView, int adapterPosition) {
                Log.d("StickyHeader", "Header Detached : " + adapterPosition);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Logout").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                pref.edit().remove("userToken").apply();
                startActivity(new Intent(getApplicationContext(), MainLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

//    private ArrayList<ListLoad> getList() {
//        // In Case if date Header
//        ArrayList<LoadModel> loadModels = new ArrayList<>();
//        loadModels.add(new LoadModel("Load #12", "Upcoming"));
//        loadModels.add(new LoadModel("Load #13", "Upcoming"));
//        loadModels.add(new LoadModel("Load #14", "Upcoming"));
//        loadModels.add(new LoadModel("Load #15", "Upcoming"));
//        loadModels.add(new LoadModel("Load #15", "Upcoming"));
//        loadModels.add(new LoadModel("Load #16", "Upcoming"));
//        loadModels.add(new LoadModel("Load #17", "Upcoming"));
//        loadModels.add(new LoadModel("Load #18", "Upcoming"));
//        loadModels.add(new LoadModel("Load #19", "Upcoming"));
//        loadModels.add(new LoadModel("Load #12", "Upcoming"));
//
//        loadModels.add(new LoadModel("#Load #23", "Completed"));
//        loadModels.add(new LoadModel("#Load #24", "Completed"));
//        loadModels.add(new LoadModel("#Load #42", "Completed"));
//        loadModels.add(new LoadModel("#Load #43", "Completed"));
//        loadModels.add(new LoadModel("#Load #44", "Completed"));
//        loadModels.add(new LoadModel("#Load #45", "Completed"));
//        loadModels.add(new LoadModel("#Load #46", "Completed"));
//        loadModels.add(new LoadModel("#Load #47", "Completed"));
//        loadModels.add(new LoadModel("#Load #48", "Completed"));
//        loadModels.add(new LoadModel("#Load #49", "Completed"));
//        loadModels.add(new LoadModel("#Load #40", "Completed"));
//        loadModels.add(new LoadModel("#Load #412", "Completed"));
//        loadModels.add(new LoadModel("#Load #413", "Completed"));
//        loadModels.add(new LoadModel("#Load #414", "Completed"));
//        loadModels.add(new LoadModel("#Load #415", "Completed"));
//        loadModels.add(new LoadModel("#Load #416", "Completed"));
//        loadModels.add(new LoadModel("#Load #417", "Completed"));
//        loadModels.add(new LoadModel("#Load #418", "Completed"));
//        loadModels.add(new LoadModel("#Load #432", "Completed"));
//        loadModels.add(new LoadModel("#Load #421", "Completed"));
//        loadModels.add(new LoadModel("#Load #422", "Completed"));
//        loadModels.add(new LoadModel("#Load #423", "Completed"));
//        loadModels.add(new LoadModel("#Load #424", "Completed"));
//
//        ArrayList<ListLoad> filteredList = new ArrayList<>();
//
//        for (int j = 0; j < loadModels.size(); j++) {
//            if (j == 0) {
//                Header header = new Header();
//                header.setHeader(loadModels.get(j).getDate());
//                Header.date = loadModels.get(j).getDate();
//                filteredList.add(header);
//            }
//
//            if (loadModels.get(j).getDate().equals(Header.date)) {
//                filteredList.add(loadModels.get(j));
//            } else {
//                Header.date = loadModels.get(j).getDate();
//                Header header = new Header();
//                header.setHeader(loadModels.get(j).getDate());
//                filteredList.add(header);
//                filteredList.add(loadModels.get(j));
//
//            }
//        }
//        return filteredList;
//    }
}