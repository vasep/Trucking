package com.example.trucking.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.trucking.CellDetails.CellActivity;
import com.example.trucking.R;
import java.util.ArrayList;

class CompletedLoadsFragmentAdapter extends RecyclerView.Adapter<CompletedLoadsFragmentAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<Integer> mData;
    private ItemClickListener mClickListener;
    private Context context;


    CompletedLoadsFragmentAdapter(Context context, ArrayList<Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CompletedLoadsFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.dashboard_recycler_cell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedLoadsFragmentAdapter.ViewHolder holder, int position) {
        holder.myTextView.setText(String.valueOf(mData.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.date_header);
            cardView = itemView.findViewById(R.id.card_view_cell );

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CellActivity.class);
                    intent.putExtra("loadId",mData.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());

        }
    }
    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}