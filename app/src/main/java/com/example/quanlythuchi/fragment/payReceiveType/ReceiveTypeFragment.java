package com.example.quanlythuchi.fragment.payReceiveType;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuchi.R;
import com.example.quanlythuchi.adapter.ReceiveTypeAdapter;
import com.example.quanlythuchi.fragment.addMore.AddMoreFragment;
import com.example.quanlythuchi.model.DanhMucThu;
import com.example.quanlythuchi.service.DanhMucThuService;
import com.example.quanlythuchi.service.LayoutService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class ReceiveTypeFragment extends Fragment {
    DanhMucThuService danhMucThuService;
    RecyclerView listDanhMucThu;
    View view;
    ImageView turnBackBtn;
    LayoutService layoutService;
    public ReceiveTypeFragment() {
        // Required empty public constructor
    }
    public static ReceiveTypeFragment newInstance(@Nullable Bundle args) {
        ReceiveTypeFragment fragment = new ReceiveTypeFragment();
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

        future.thenAccept(results -> listDanhMucThu.post(() -> {
            ReceiveTypeAdapter receiveTypeAdapter = new ReceiveTypeAdapter(getContext(), results, item -> {
                Bundle bundle = new Bundle();
                Bundle args = getArguments();

                bundle.putSerializable("muc_thu", item);
                bundle.putBundle("old_value", args);
                AddMoreFragment addMoreFragment = new AddMoreFragment();
                addMoreFragment.setArguments(bundle);

                layoutService.change(R.id.main_fragmentBody, addMoreFragment);
            });
            listDanhMucThu.setAdapter(receiveTypeAdapter);
            listDanhMucThu.setItemAnimator(new SlideInLeftAnimator());
            listDanhMucThu.setLayoutManager(new LinearLayoutManager(getContext()));
        })).exceptionally(e -> {
            Log.e("TAG", "onCreate: " + e);
            return null;
        });
    }

    private void onTurnBackBtnClick() {
        turnBackBtn.setOnClickListener(view -> getParentFragmentManager().popBackStack());
    }
}