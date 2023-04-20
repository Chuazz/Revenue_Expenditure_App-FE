package com.example.quanlythuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.model.DanhMucThu;

import java.util.List;

public class PayTypeAdapter extends RecyclerView.Adapter<PayTypeAdapter.ViewHolder> {
    List<DanhMucThu> danhMucThus;
    Context context;

    public PayTypeAdapter(Context context, List<DanhMucThu> danhMucThus) {
        this.danhMucThus = danhMucThus;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.pay_type_item, null);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMucThu contact = danhMucThus.get(position);
        holder.nameType.setText(contact.getTenDMThu());
    }

    @Override
    public int getItemCount() {
        return danhMucThus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameType = itemView.findViewById(R.id.payTypeItem_nameType);
        }
    }
}
