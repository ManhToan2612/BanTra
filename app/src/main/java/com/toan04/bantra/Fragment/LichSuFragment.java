package com.toan04.bantra.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.toan04.bantra.DAO.DonHangDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.DonHangAdapter;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;


public class LichSuFragment extends Fragment {

    private ArrayList<DonHang> list = new ArrayList<>();
    private DonHangDAO dao;

    private DonHangAdapter adapterDonHang;

    RecyclerView rcv;

    public LichSuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lich_su, container, false);
        SharedPreferences preferences = requireActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
        String maad = preferences.getString("maAD", "");

        rcv = view.findViewById(R.id.rcv_Lich_Su_Don_Hang);
        dao = new DonHangDAO(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        rcv.setLayoutManager(layoutManager);
        list = dao.getDonHangByMaTaiKhoan(maad);
        adapterDonHang = new DonHangAdapter(list, getContext());

        rcv.setAdapter(adapterDonHang);
        adapterDonHang.setOnItemClick(new DonHangAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position) {
                DonHang donHang = list.get(position);
                int maDonHang = donHang.getMaDonHang();

                Bundle bundle = new Bundle();
                bundle.putInt("maDonHang",maDonHang);
                DonHangCTFragment donHangChiTietFragment = new DonHangCTFragment();
                donHangChiTietFragment.setArguments(bundle);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frglayout, donHangChiTietFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return view;
    }
}