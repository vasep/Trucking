package com.example.trucking.Dashboard;

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
import com.example.trucking.HttpRequests.RetroClient;
import com.example.trucking.R;
import com.example.trucking.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingLoadsFragment extends Fragment {
    UpcomingLoadsFragmentAdapter adapter;
    ArrayList<Integer> arrayLoads = new ArrayList<>();
    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcoming_fragment_layout, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        fetchUpcomingLoads(0);

        return view;
    }

    private void fetchUpcomingLoads(int page) {
        Call<JsonObject> call = RetroClient.getApiService().getLoadsForDriver("Bearer " +
                Constants.userToken, "mobile/drivers/loads/paginated?pageNumber=" + page + "&pageSize=20&status=Upcoming");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Context context = getActivity();
                if (context != null && response.body() != null) {
                    JsonArray jsonArrayObject = response.body().getAsJsonArray("genericModel");
                    prepareRecyclerViewData(jsonArrayObject);
                    showLoads(context);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(
                        "Retrofit C", t.getMessage());
            }
        });
    }

    private void prepareRecyclerViewData(JsonArray jsonArrayObject) {
        for (int i = 0; i < jsonArrayObject.size(); i++) {
            JsonElement idElement = jsonArrayObject.get(i);
            JsonObject idElementObject = (JsonObject) idElement;
            int id = Integer.valueOf(idElementObject.get("id").toString());
            arrayLoads.add(id);
        }
    }

    private void showLoads(Context context) {
        adapter = new UpcomingLoadsFragmentAdapter(context, arrayLoads);

        progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
