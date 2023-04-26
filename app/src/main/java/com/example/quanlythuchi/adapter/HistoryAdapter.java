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

import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<LichSu> lichSus;

    public HistoryAdapter(List<LichSu> lichSus) {
        this.lichSus = lichSus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSu lichSu = lichSus.get(position);
        List<GiaoDich> giaoDichs = lichSu.getGiaoDich();

        String newDate = lichSu.get_thu() + " - " + lichSu.get_ngay() +
                        '/' + lichSu.get_thang() + '/' + lichSu.get_nam() +
                        "  -- Thu: " + lichSu.getTongThu() + "  -- Chi: " + lichSu.getTongChi();
        holder.label.setText(newDate);

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(giaoDichs);
        holder.items.setAdapter(historyItemAdapter);
    }

    @Override
    public int getItemCount() {
        return lichSus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView label;
        RecyclerView items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            label = itemView.findViewById(R.id.historyGroup_label);
            items = itemView.findViewById(R.id.historyGroup_list);
        }
    }
}
