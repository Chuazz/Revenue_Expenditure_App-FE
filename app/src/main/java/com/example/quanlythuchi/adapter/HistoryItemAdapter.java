package com.example.quanlythuchi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;

import java.util.List;

public class HistoryItemAdapter  extends RecyclerView.Adapter<HistoryItemAdapter.ViewHolder> {

    List<GiaoDich> items;

    public HistoryItemAdapter(List<GiaoDich> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GiaoDich giaoDich = items.get(position);

        holder.label.setText(giaoDich.getDanhMuc());
        holder.value.setText(String.valueOf(giaoDich.getTien()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.historyItem_label);
            value = itemView.findViewById(R.id.historyItem_value);
        }
    }
}
