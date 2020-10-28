package com.example.freightviewer.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freightviewer.Dashboard.loadmodel.Header;
import com.example.freightviewer.Dashboard.loadmodel.ListLoad;
import com.example.freightviewer.Dashboard.loadmodel.LoadModel;
import com.example.freightviewer.R;

import java.util.ArrayList;

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ListLoad> data;
    private Context context;
    private LayoutInflater mInflater;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    DashboardRecyclerAdapter(ArrayList<ListLoad> data, Context context){
        this.data = data;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            // view for normal data.

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_single_row_date, parent, false);
            return new VHHeader(view);
        } else {
            // view type for month or date header

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycler_cell, parent, false);
            return new VHItem(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VHHeader) {
            // VHHeader VHheader = (VHHeader)holder;
//            Header  currentItem = (Header) data.get(position);
//            VHHeader VHheader = (VHHeader)holder;
//            VHheader.txtTitle.setText(currentItem.getHeader());
        } else if (holder instanceof VHItem){
            LoadModel currentItem = (LoadModel) data.get(position);
        VHItem VHitem = (VHItem)holder;
        VHitem.txtName.setText(currentItem.getLoadname());
    }
}

    private boolean isPositionHeader(int position) {
        return data.get(position) instanceof Header;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView) itemView.findViewById(R.id.date_header);
        }
    }
    class VHItem extends RecyclerView.ViewHolder{
        TextView txtName;
        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (TextView) itemView.findViewById(R.id.date_header);
        }
    }


//    public class ItemViewHolder extends RecyclerView.ViewHolder {
//        TextView loadText;
//
//        public ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//            loadText = itemView.findViewById(R.id.date_header);
//        }
//    }
}
