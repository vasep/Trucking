package com.example.freightviewer.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;

import com.example.freightviewer.Dashboard.loadmodel.Header;
import com.example.freightviewer.Dashboard.loadmodel.ListLoad;
import com.example.freightviewer.Dashboard.loadmodel.LoadModel;
import com.example.freightviewer.R;
import com.example.freightviewer.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class DashboardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DashboardRecyclerAdapter dashboardRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.dashboard_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        dashboardRecyclerAdapter = new DashboardRecyclerAdapter(getList(), getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(dashboardRecyclerAdapter);

        RecyclerItemDecoration recyclerItemDecoration = new RecyclerItemDecoration(this,getResources().getDimensionPixelSize(R.dimen.header_height),true,null);
        recyclerView.addItemDecoration(recyclerItemDecoration);
    }

    private RecyclerItemDecoration.SectionCallback getSectionCallback(final ArrayList<ListLoad> list) {
        return new RecyclerItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
//                return pos==0 || list.get(pos).get("Title")!=list.get(pos-1).get("Title");
                return  list.get(position ) instanceof Header;
            }

            @Override
            public String getSectionHeaderName(int pos) {
                if (list.get(pos) instanceof Header){
                    Header  currentItem = (Header) list.get(pos);
                    return currentItem.getHeader();
                } else {
                    return "null";
                }
            }
        };
    }

    private ArrayList<ListLoad> getList() {

        ArrayList<LoadModel> loadModels = new ArrayList<>();
        loadModels.add(new LoadModel("Tura 1", 2));
        loadModels.add(new LoadModel("Tura 2", 2));
        loadModels.add(new LoadModel("Tura 3", 2));
        loadModels.add(new LoadModel("Tura 4", 3));
        loadModels.add(new LoadModel("Tura 5", 3));
        loadModels.add(new LoadModel("Tura 6", 3));
        loadModels.add(new LoadModel("Tura 7", 3));
        loadModels.add(new LoadModel("Tura 4", 3));
        loadModels.add(new LoadModel("Tura 5", 10));
        loadModels.add(new LoadModel("Tura 6", 3));
        loadModels.add(new LoadModel("Tura 7", 7));
        loadModels.add(new LoadModel("Tura 4", 3));
        loadModels.add(new LoadModel("Tura 5", 5));
        loadModels.add(new LoadModel("Tura 6", 4));
        loadModels.add(new LoadModel("Tura 7", 3));
        loadModels.add(new LoadModel("Tura 4", 1));
        loadModels.add(new LoadModel("Tura 5", 3));
        loadModels.add(new LoadModel("Tura 6", 2));
        loadModels.add(new LoadModel("Tura 7", 3));

        ArrayList<ListLoad> filteredList = new ArrayList<>();

        for (int j = 0; j < loadModels.size(); j++) {
            if (j == 0) {
                Header header = new Header();
                header.setHeader(Integer.toString(loadModels.get(j).getDate()));
                Header.date = loadModels.get(j).getDate();
                filteredList.add(header);
            }

            if (loadModels.get(j).getDate() == Header.date) {
                filteredList.add(loadModels.get(j));
            } else {
                Header.date = loadModels.get(j).getDate();
                Header header = new Header();
                header.setHeader(Integer.toString(loadModels.get(j).getDate()));
                filteredList.add(header);
                filteredList.add(loadModels.get(j));

            }
        }
        return filteredList;
    }
}