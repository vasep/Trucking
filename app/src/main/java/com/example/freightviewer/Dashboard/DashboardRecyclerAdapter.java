package com.example.freightviewer.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freightviewer.CellDetails.CellActivity;
import com.example.freightviewer.Dashboard.loadmodel.Header;
import com.example.freightviewer.Dashboard.loadmodel.ListLoad;
import com.example.freightviewer.Dashboard.loadmodel.LoadModel;
import com.example.freightviewer.R;
import com.example.freightviewer.StickHeaderItemDecoration;
import com.yuyang.stickyheaders.AdapterDataProvider;

import java.util.ArrayList;
import java.util.List;

public class DashboardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDataProvider {
    private ArrayList<ListLoad> data;
    private Context context;
    private LayoutInflater mInflater;
    public int loadId;
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
//             VHHeader VHheader = (VHHeader)holder;
            Header  currentItem = (Header) data.get(position);
            VHHeader vhHeader = (VHHeader)holder;
            if (currentItem.getHeader().equals("Completed")){
                vhHeader.itemView.setBackgroundColor(Color.rgb(152,200,200));
            }else {
                vhHeader.itemView.setBackgroundColor(Color.rgb(254,194,0));
            }

            vhHeader.txtTitle.setText(currentItem.getHeader());
        } else if (holder instanceof VHItem){
            LoadModel currentItem = (LoadModel) data.get(position);
            loadId = currentItem.getId();
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

    @Override
    public List<?> getAdapterData() {
        return data;
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
        CardView cardView;
        public VHItem(View itemView) {
            super(itemView);
            this.cardView = itemView.findViewById(R.id.card_view_cell);
            this.txtName = (TextView) itemView.findViewById(R.id.date_header);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context.getApplicationContext(), CellActivity.class).putExtra("loadId",loadId));
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            if(v == cardView)
//                onDasCellClickListener.onItemClick(v,getAdapterPosition());
//        }
    }

//    public interface OnDasCellClickListener {
//        void onItemClick(View view, int position);
//    }
//    DashboardRecyclerAdapter(ArrayList<ListLoad> data, Context context){
//        this.data = data;
//        this.context = context;
//        this.mInflater = LayoutInflater.from(context);
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == TYPE_HEADER) {
//            // view for normal data.
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_single_row_date, parent, false);
//            return new VHHeader(view);
//        } else {
//            // view type for month or date header
//
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_recycler_cell, parent, false);
//            return new VHItem(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof VHHeader) {
////             VHHeader VHheader = (VHHeader)holder;
//            Header  currentItem = (Header) data.get(position);
//            VHHeader vhHeader = (VHHeader)holder;
//            vhHeader.txtTitle.setText(currentItem.getHeader());
//        } else if (holder instanceof VHItem){
//            LoadModel currentItem = (LoadModel) data.get(position);
//        VHItem VHitem = (VHItem)holder;
//        VHitem.txtName.setText(currentItem.getLoadname());
//    }
//}
//
//    private boolean isPositionHeader(int position) {
//        return data.get(position) instanceof Header;
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (isPositionHeader(position))
//            return TYPE_HEADER;
//        return TYPE_ITEM;
//    }
//
//    class VHHeader extends RecyclerView.ViewHolder{
//        TextView txtTitle;
//        public VHHeader(View itemView) {
//            super(itemView);
//            this.txtTitle = (TextView) itemView.findViewById(R.id.date_header);
//        }
//    }
//    class VHItem extends RecyclerView.ViewHolder{
//        TextView txtName;
//        public VHItem(View itemView) {
//            super(itemView);
//            this.txtName = (TextView) itemView.findViewById(R.id.date_header);
//        }
//    }
}
