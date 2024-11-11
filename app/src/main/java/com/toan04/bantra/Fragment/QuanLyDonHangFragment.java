package com.toan04.bantra.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toan04.bantra.DAO.DonHangCTDAO;
import com.toan04.bantra.DAO.DonHangDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.DonHangAdapter;
import com.toan04.bantra.model.DonHang;
import com.toan04.bantra.model.DonHangChiTiet;

import java.util.ArrayList;


public class QuanLyDonHangFragment extends Fragment {
    RecyclerView rcvdonhang;
    DonHangAdapter donHangAdapter;
    private ArrayList<DonHang> list = new ArrayList<>();
    DonHangDAO donHangDAO;
    private DonHangCTDAO chiTietDao;

    public QuanLyDonHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);
        rcvdonhang= view.findViewById(R.id.rcvQLDH);
        donHangDAO= new DonHangDAO(getContext());
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        rcvdonhang.setLayoutManager(layoutManager);
        list = donHangDAO.getDSDonHang();
        donHangAdapter= new DonHangAdapter(list, getContext());
        rcvdonhang.setAdapter(donHangAdapter);
        chiTietDao = new DonHangCTDAO(getContext());

        donHangAdapter.setOnItemClick(new DonHangAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang", maDonHang);
                DonHangCTFragment frgDonHangChiTiet = new DonHangCTFragment();
                frgDonHangChiTiet.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frglayout, frgDonHangChiTiet);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}