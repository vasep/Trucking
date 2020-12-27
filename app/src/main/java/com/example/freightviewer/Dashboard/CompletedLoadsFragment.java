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
import com.example.freightviewer.HttpRequests.RetroClient;
import com.example.freightviewer.R;
import com.example.freightviewer.Utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedLoadsFragment extends Fragment {
    private ArrayList<Integer> arrayList = new ArrayList<>();
    CompletedLoadsFragmentAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.upcoming_fragment_layout, container, false);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        fetchCompletedLoads(0);

        return view;
    }

    private void fetchCompletedLoads(int page) {
        Call<JsonObject> call = RetroClient.getApiService().getLoadsForDriver("Bearer " +
                Constants.userToken, "mobile/drivers/loads/paginated?pageNumber="+page+"&pageSize=20&status=Completed");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Context context = getActivity();
                if (context != null && response.body() != null) {
                    JsonArray loadsArray = response.body().getAsJsonArray("genericModel");
                    prepareRecyclerViewData(loadsArray);
                    showLoads(context);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Retrofit C", t.getMessage());
            }
        });
    }

    private void prepareRecyclerViewData(JsonArray jsonArrayObject) {
        for (int i = 0; i < jsonArrayObject.size(); i++) {
            JsonObject idElementObject = (JsonObject) jsonArrayObject.get(i);
            int id = Integer.parseInt(idElementObject.get("id").toString());
            arrayList.add(id);
        }
    }

    private void showLoads(Context context) {
        adapter = new CompletedLoadsFragmentAdapter(context, arrayList);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
    }
}

