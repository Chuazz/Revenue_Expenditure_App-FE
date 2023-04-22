package com.example.quanlythuchi.fragment.payReceiveType;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.PayTypeAdapter;
import com.example.quanlythuchi.callback.listener.OnPayItemClickListener;
import com.example.quanlythuchi.callback.danhmucchi.FindCallback;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.model.DanhMucChi;
import com.example.quanlythuchi.service.DanhMucChiService;
import com.example.quanlythuchi.service.LayoutService;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class PayTypeFragment extends Fragment {
    DanhMucChiService danhMucChiservice;
    RecyclerView listDanhMucChi;
    List danhMucChis;
    View view;
    ImageView turnBackBtn;
    LayoutService layoutService;

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

        onCreate();
        onTurnBackBtnClick();

        return view;
    }

    private void onTurnBackBtnClick() {
        turnBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    void onCreate(){
        danhMucChiservice.findAll(new FindCallback() {
            @Override
            public void onSuccess(List results) {
                danhMucChis = results;

                    PayTypeAdapter payTypeAdapter = new PayTypeAdapter(getContext(), danhMucChis, new OnPayItemClickListener() {
                    @Override
                    public void onItemClick(DanhMucChi item) {
                        Bundle bundle = new Bundle();

                        bundle.putSerializable("muc_chi", item);
                        AddMoreFragment addMoreFragment = new AddMoreFragment();
                        addMoreFragment.setArguments(bundle);

                        layoutService.change(R.id.main_fragmentBody, addMoreFragment);
                    }
                });

                listDanhMucChi.setAdapter(payTypeAdapter);
                listDanhMucChi.setItemAnimator(new SlideInLeftAnimator());
                listDanhMucChi.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onFailure() {

            }
        });
    }
}