package com.example.quanlythuchi.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.model.GiaoDich;
import com.example.quanlythuchi.model.LichSu;
import com.example.quanlythuchi.util.Commas;

import org.w3c.dom.Text;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSu lichSu = lichSus.get(position);
        List<GiaoDich> giaoDichs = lichSu.getGiaoDich();

        String newDate = lichSu.get_thu() + " - " + +
                        '/' + lichSu.get_thang() + '/' + lichSu.get_nam() +
                        "  -- Thu: " + lichSu.getTongThu() + "  -- Chi: " + lichSu.getTongChi();

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

        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(giaoDichs);
        holder.items.setAdapter(historyItemAdapter);
    }

    @Override
    public int getItemCount() {
        return lichSus.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
