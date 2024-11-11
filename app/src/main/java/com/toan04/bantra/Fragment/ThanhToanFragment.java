package com.toan04.bantra.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.toan04.bantra.DAO.DonHangCTDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.ThanhToanAdapter;
import com.toan04.bantra.model.DonHangChiTiet;

import java.util.ArrayList;


public class ThanhToanFragment extends Fragment {


    private ArrayList<DonHangChiTiet> list = new ArrayList<>();
    private DonHangCTDAO chiTietDao;
    private ThanhToanAdapter adapterThanhToan;

    RecyclerView rcvThanhToan;
    Button btntieptucmua;

    public ThanhToanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_thanh_toan, container, false);
        rcvThanhToan = view.findViewById(R.id.rcvThanhToan);
        btntieptucmua=view.findViewById(R.id.btntieptucmua);

        chiTietDao = new DonHangCTDAO(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvThanhToan.setLayoutManager(layoutManager);
        adapterThanhToan = new ThanhToanAdapter(list,getContext());
        rcvThanhToan.setAdapter(adapterThanhToan);
        adapterThanhToan.notifyDataSetChanged();

        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang", 0);
            Log.d("maDonHang", String.valueOf(maDonHang));
            if (maDonHang != 0) {
                list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterThanhToan = new ThanhToanAdapter(list, getContext());
                rcvThanhToan.setAdapter(adapterThanhToan);

            }
        }

        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GioHangFragment frgGioHang = new GioHangFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frglayout,frgGioHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
}