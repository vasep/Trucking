package com.example.freightviewer.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freightviewer.HttpRequests.JSONPlaceHolderApi;
import com.example.freightviewer.HttpRequests.RetroClient;
import com.example.freightviewer.R;
import com.example.freightviewer.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcomingLoadsFragment extends Fragment {
    UpcomingLoadsFragmentAdapter adapter;
    ArrayList<Integer> arrayLoads = new ArrayList<>();
    ProgressBar progressBar;

    public UpcomingLoadsFragment(){
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.upcoming_fragment_layout, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        Call<JsonObject> call = RetroClient.getApiService().getLoadsForDriver("Bearer " +
                Constants.userToken,"mobile/drivers/loads/paginated?pageNumber=0&pageSize=20&status=Upcoming");
        final RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Enqueue to call on the back thread
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Context context = getActivity();
                if (context != null) {
                    if(!response.isSuccessful()){
                        Log.d("RetrofitA",""+response.code());
                        return;
                    }

                    JsonArray jsonArrayObject = response.body().getAsJsonArray("genericModel");
                    ArrayList<Integer> loadsIdArray = new ArrayList<>();

                    for (int i = 0; i < jsonArrayObject.size(); i++) {

                        JsonElement idElement = jsonArrayObject.get(i);
                        JsonObject idElementObject = (JsonObject) idElement;
                        int id = Integer.valueOf(idElementObject.get("id").toString());
                        loadsIdArray.add(id);
                    }
                    arrayLoads = loadsIdArray;
                    // set up the RecyclerView

                    adapter = new UpcomingLoadsFragmentAdapter(context, loadsIdArray);

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Retrofit C",t.getMessage());
            }
        });
        return view;
    }
}
