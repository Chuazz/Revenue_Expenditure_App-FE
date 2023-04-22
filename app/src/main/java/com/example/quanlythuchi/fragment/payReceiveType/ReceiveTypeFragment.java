package com.example.quanlythuchi.fragment.payReceiveType;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.PayTypeAdapter;
import com.example.quanlythuchi.adapter.ReceiveTypeAdapter;
import com.example.quanlythuchi.callback.danhmucthu.FindCallback;
import com.example.quanlythuchi.callback.listener.OnPayItemClickListener;
import com.example.quanlythuchi.callback.listener.OnReceiveItemClickListener;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.service.DanhMucThuService;
import com.example.quanlythuchi.service.LayoutService;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class ReceiveTypeFragment extends Fragment {
    DanhMucThuService danhMucThuService;
    RecyclerView listDanhMucThu;
    List danhMucThus;
    View view;
    ImageView turnBackBtn;
    LayoutService layoutService;
    public ReceiveTypeFragment() {
        // Required empty public constructor
    }
    public static ReceiveTypeFragment newInstance() {
        ReceiveTypeFragment fragment = new ReceiveTypeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_receive_type, container, false);
        listDanhMucThu = view.findViewById(R.id.chooseReceiveType_listItem);
        danhMucThuService = new DanhMucThuService();
        turnBackBtn = view.findViewById(R.id.chooseReceiveType_turnBackBtn);
        layoutService = new LayoutService(getParentFragmentManager());

        onCreate();
        onTurnBackBtnClick();

        return view;
    }

    void onCreate(){
        CompletableFuture<List<DanhMucThu>> future = danhMucThuService.findAll();
        List<DanhMucThu> results = future.join();

        ReceiveTypeAdapter receiveTypeAdapter = new ReceiveTypeAdapter(getContext(), results, new OnReceiveItemClickListener() {
            @Override
            public void onItemClick(DanhMucThu item) {
                Bundle bundle = new Bundle();

                bundle.putSerializable("muc_thu", item);
                AddMoreFragment addMoreFragment = new AddMoreFragment();
                addMoreFragment.setArguments(bundle);

                layoutService.change(R.id.main_fragmentBody, addMoreFragment);
            }
        });

        listDanhMucThu.setAdapter(receiveTypeAdapter);
        listDanhMucThu.setItemAnimator(new SlideInLeftAnimator());
        listDanhMucThu.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void onTurnBackBtnClick() {
        turnBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }
}