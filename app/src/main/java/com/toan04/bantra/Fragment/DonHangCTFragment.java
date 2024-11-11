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
import android.widget.TextView;

import com.toan04.bantra.DAO.DonHangCTDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.DonHangChiTietAdapter;
import com.toan04.bantra.model.DonHangChiTiet;

import java.util.ArrayList;

public class DonHangCTFragment extends Fragment {

    private ArrayList<DonHangChiTiet> list = new ArrayList<>();

    private DonHangChiTietAdapter adapterDonHangChiTiet;
    DonHangCTDAO chiTietDao;
    RecyclerView rcvDonHangChiTiet;
    TextView btnback;
    public DonHangCTFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_hang_c_t, container, false);
        rcvDonHangChiTiet = view.findViewById(R.id.rcv_don_hang_chiTiet);
        btnback = view.findViewById(R.id.btnback);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvDonHangChiTiet.setLayoutManager(layoutManager);
        adapterDonHangChiTiet = new DonHangChiTietAdapter(list,getContext());
        rcvDonHangChiTiet.setAdapter(adapterDonHangChiTiet);
        adapterDonHangChiTiet.notifyDataSetChanged();
        chiTietDao = new DonHangCTDAO(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            int maDonHang = bundle.getInt("maDonHang");
            Log.d("maDonHang", String.valueOf(maDonHang));
            if (maDonHang != 0) {
                list = chiTietDao.getChiTietDonHangByMaDonHang(maDonHang);
                adapterDonHangChiTiet = new DonHangChiTietAdapter(list, getContext());
                rcvDonHangChiTiet.setAdapter(adapterDonHangChiTiet);

            }
        }

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuanLyDonHangFragment frgQuanLyDonHang = new QuanLyDonHangFragment();//fragment được chuyển đến sau khi ấn
                FragmentManager fragmentManager=getParentFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frglayout,frgQuanLyDonHang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}