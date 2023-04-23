package com.example.quanlythuchi.fragment.payReceiveType;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.PayTypeAdapter;
import com.example.quanlythuchi.callback.sendData.TypeDataPassListener;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.LayoutService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


public class PayTypeFragment extends Fragment implements TypeDataPassListener {
    DanhMucChiService danhMucChiservice;
    RecyclerView listDanhMucChi;
    List<DanhMucChi> danhMucChis;
    View view;
    ImageView turnBackBtn;
    LayoutService layoutService;
    FragmentManager fragmentManager;

    public PayTypeFragment() {
        // Required empty public constructor
    }

    public static PayTypeFragment newInstance(@Nullable Bundle args) {
        PayTypeFragment fragment = new PayTypeFragment();
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
        turnBackBtn.setOnClickListener(view -> fragmentManager.popBackStack());
    }

    void onCreate(){
        CompletableFuture<List<DanhMucChi>> future = danhMucChiservice.findAll();
        danhMucChis = future.join();
        PayTypeAdapter payTypeAdapter = new PayTypeAdapter(getContext(), danhMucChis, item -> {
            Bundle bundle = new Bundle();
            Bundle args = getArguments();

            bundle.putSerializable("muc_chi", item);
            bundle.putBundle("old_value", args);

            AddMoreFragment addMoreFragment = new AddMoreFragment();
            addMoreFragment.setArguments(bundle);
            layoutService.change(R.id.main_fragmentBody, addMoreFragment);
        });

        listDanhMucChi.setAdapter(payTypeAdapter);
        listDanhMucChi.setItemAnimator(new SlideInLeftAnimator());
        listDanhMucChi.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDataPass(DanhMucChi danhMucChi) {

    }
}