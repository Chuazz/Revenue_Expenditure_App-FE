package com.example.quanlythuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.callback.OnTypeItemClickListener;
import com.example.quanlythuchi.model.DanhMucChi;

import java.util.List;

public class PayTypeAdapter extends RecyclerView.Adapter<PayTypeAdapter.ViewHolder> {
    List<DanhMucChi> danhMucChis;
    Context context;
    private OnTypeItemClickListener listener;

    public PayTypeAdapter(Context context, List<DanhMucChi> danhMucChis, OnTypeItemClickListener listener) {
        this.danhMucChis = danhMucChis;
        this.context = context;
        this.listener = listener;
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
        DanhMucChi contact = danhMucChis.get(position);
        holder.nameType.setText(contact.getTenDMChi());
        holder.bind(danhMucChis.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return danhMucChis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameType = itemView.findViewById(R.id.payTypeItem_nameType);
        }

        public void bind(final DanhMucChi item, final OnTypeItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);

                }
            });
        }
    }
}
