package com.example.quanlythuchi.fragment.payReceiveType;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.PayTypeAdapter;
import com.example.quanlythuchi.callback.listener.OnPayItemClickListener;
import com.example.quanlythuchi.callback.sendData.TypeDataPassListener;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.LayoutService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


public class PayTypeFragment extends Fragment implements TypeDataPassListener {
    DanhMucChiService danhMucChiservice;
    RecyclerView listDanhMucChi;
    List danhMucChis;
    View view;
    ImageView turnBackBtn;
    LayoutService layoutService;
    FragmentManager fragmentManager;

    public PayTypeFragment() {
        // Required empty public constructor
    }

    public static PayTypeFragment newInstance() {
        PayTypeFragment fragment = new PayTypeFragment();
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
        view = inflater.inflate(R.layout.fragment_pay_type, container, false);
        listDanhMucChi = view.findViewById(R.id.choosePayType_listItem);
        danhMucChiservice = new DanhMucChiService();
        turnBackBtn = view.findViewById(R.id.choosePayType_turnBackBtn);
        layoutService = new LayoutService(getParentFragmentManager());
        fragmentManager = getParentFragmentManager();

        onCreate();
        onTurnBackBtnClick();

        return view;
    }

    private void onTurnBackBtnClick() {
        turnBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutService.loadAddMore();
            }
        });
    }

    void onCreate(){
        CompletableFuture<List<DanhMucChi>> future = danhMucChiservice.findAll();
        danhMucChis = future.join();
        PayTypeAdapter payTypeAdapter = new PayTypeAdapter(getContext(), danhMucChis, new OnPayItemClickListener() {
            @Override
            public void onItemClick(DanhMucChi item) {
                Bundle bundle = new Bundle();

                bundle.putSerializable("muc_chi", item);
                AddMoreFragment addMoreFragment = new AddMoreFragment();
                addMoreFragment.setArguments(bundle);

                layoutService.change(R.id.main_fragmentBody, addMoreFragment);
                //fragmentManager.popBackStack();
            }
        });

        listDanhMucChi.setAdapter(payTypeAdapter);
        listDanhMucChi.setItemAnimator(new SlideInLeftAnimator());
        listDanhMucChi.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDataPass(DanhMucChi danhMucChi) {

    }
}