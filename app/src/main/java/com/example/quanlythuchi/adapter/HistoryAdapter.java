package com.example.quanlythuchi.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.service.LayoutService;
import com.example.quanlythuchi.util.Commas;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<LichSu> lichSus;
    LayoutService layoutService;
    FragmentManager fragmentManager;

    public HistoryAdapter(List<LichSu> lichSus, FragmentManager fragmentManager) {
        this.lichSus = lichSus;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_group, parent, false);
        layoutService = new LayoutService(this.fragmentManager);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSu lichSu = lichSus.get(position);
        List<GiaoDich> giaoDichs = lichSu.getGiaoDich();

        if(lichSu.isToday()){
            holder.day.setText("Hôm nay");
        }
        else{
            holder.day.setText(lichSu.get_thu());
        }

        if(Integer.parseInt(lichSu.get_thang()) < 10){
            holder.monthYear.setText(0 + lichSu.get_thang() + "/" + lichSu.get_nam());
        }
        else{
            holder.monthYear.setText(lichSu.get_thang() + "/" + lichSu.get_nam());
        }

        holder.date.setText(lichSu.get_ngay());
        holder.totalReceive.setText(Commas.add(lichSu.getTongThu()) + "đ");
        holder.totalPay.setText(Commas.add(lichSu.getTongChi()) + "đ");

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(giaoDichs, item -> {
            layoutService.loadAddMore(lichSu.getNgay(), item, true);
        });
        holder.items.setAdapter(historyItemAdapter);
    }

    @Override
    public int getItemCount() {
        return lichSus.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView day;
        TextView monthYear;
        TextView totalReceive;
        TextView totalPay;
        RecyclerView items;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.historyGroup_date);
            day = itemView.findViewById(R.id.historyGroup_day);
            monthYear = itemView.findViewById(R.id.historyGroup_monthYear);
            totalReceive = itemView.findViewById(R.id.historyGroup_totalReceive);
            totalPay = itemView.findViewById(R.id.historyGroup_totalPay);
            items = itemView.findViewById(R.id.historyGroup_list);
        }
    }
}
