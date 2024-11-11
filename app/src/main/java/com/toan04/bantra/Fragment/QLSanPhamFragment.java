package com.toan04.bantra.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.toan04.bantra.DAO.LoaiSanPhamDAO;
import com.toan04.bantra.DAO.SanPhamDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.SanPhamHAdapter;
import com.toan04.bantra.model.SanPham;
import com.toan04.bantra.model.loaiSanPham;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSanPhamFragment extends Fragment {

    RecyclerView rcv;

    FloatingActionButton fltAdd;
    SanPhamDAO dao;
    SanPhamHAdapter adapter;
    ArrayList<SanPham> list= new ArrayList<>();


    public QLSanPhamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_q_l_san_pham, container, false);
        rcv= view.findViewById(R.id.rcvqlsanpham);
        fltAdd = view.findViewById(R.id.add_sp);
        dao= new SanPhamDAO(getContext());
        list= dao.getDSSanPham();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new SanPhamHAdapter(getContext(),list,getDSLoaiGiay());
        rcv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        view.findViewById(R.id.add_sp).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialogAllSanPham();
//            }
//        });

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAllSanPham();
            }
        });
        return view;
    }

    private void dialogAllSanPham(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sanpham,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenSanPham = view.findViewById(R.id.in_addTenSP);
        TextInputLayout in_GiaThue = view.findViewById(R.id.in_addGiaTien);
        TextInputLayout in_SoLuong = view.findViewById(R.id.in_addSoLuong);
        TextInputLayout in_Anh = view.findViewById(R.id.in_addAnh);
        TextInputEditText ed_TenSanPham = view.findViewById(R.id.ed_addTenSP);
        TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_addGiaTien);
        TextInputEditText ed_SoLuong = view.findViewById(R.id.ed_addSoLuong);
        TextInputEditText ed_Anh = view.findViewById(R.id.ed_addAnh);
        Spinner spnSanPham = view.findViewById(R.id.spnSanPham1);
        Button addSP = view.findViewById(R.id.SP_add);
        Button cancel = view.findViewById(R.id.SP_Cancel);
        in_Anh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (ed_Anh.getText().toString().trim().isEmpty()){
                        in_Anh.setError("Vui lòng không để trống link ảnh");
                    }else {
                        in_Anh.setError(null);
                    }
                }
            }
        });



        ed_SoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_SoLuong.setError("Vui lòng không để trống số lượng sản phẩm");
                }else{
                    in_SoLuong.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        ed_TenSanPham.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_TenSanPham.setError("Vui lòng không để trống tên sản phẩm");
                }else{
                    in_TenSanPham.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_GiaThue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_GiaThue.setError("Vui lòng không để trống giá tiền");
                }else{
                    in_GiaThue.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


//        ed_Anh.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(charSequence.length() == 0){
//                    in_Anh.setError("Vui lòng không để trống link ảnh");
//                }else{
//                    in_Anh.setError(null);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });


        ArrayList<HashMap<String, Object>> dsLoaiSanPhams = getDSLoaiGiay();
        if (dsLoaiSanPhams != null){
            SimpleAdapter simpleAdapter = new SimpleAdapter(
                    getContext(),
                    getDSLoaiGiay(),
                    android.R.layout.simple_list_item_1,
                    new String[]{"tenLoai"},
                    new int[]{android.R.id.text1}
            );
            spnSanPham.setAdapter(simpleAdapter);
        }



        addSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = ed_TenSanPham.getText().toString();
                String checktien = ed_GiaThue.getText().toString();
                String soLuongSP = ed_SoLuong.getText().toString();
                String anhSP = ed_Anh.getText().toString();
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSanPham.getSelectedItem();
                int maloai = (int) hs.get("maLoai");


                if(tensach.isEmpty() || checktien.isEmpty() || soLuongSP.isEmpty() || anhSP.isEmpty()){
                    if(tensach.equals("")){
                        in_TenSanPham.setError("Vui lòng không để trống tên sản phẩm");
                    }else{
                        in_TenSanPham.setError(null);
                    }

                    if(checktien.equals("")){
                        in_GiaThue.setError("Vui lòng không để trống giá tiền");
                    }else{
                        in_GiaThue.setError(null);
                    }

                    if(soLuongSP.equals("")){
                        in_SoLuong.setError("Vui lòng không để trống số lượng sản phẩm");
                    }else{
                        in_SoLuong.setError(null);
                    }
                    if (ed_Anh.getText().toString().trim().isEmpty()){
                        in_Anh.setError("Vui lòng không để trống link ảnh");
                    }else {
                        in_Anh.setError(null);
                    }

                }else{
                    try {
                        int tien = Integer.parseInt(checktien);
                        int soluong= Integer.parseInt(soLuongSP);
                        boolean check = dao.insert(tensach, tien, maloai,soluong,anhSP);
                        if (check) {
                            loadData();
                            Toast.makeText(getContext(), "Thêm thành công sản phẩm", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getDSSanPham());

                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm không thành công sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Số tiền hoặc số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    private ArrayList<HashMap<String , Object>> getDSLoaiGiay(){
        LoaiSanPhamDAO loaiSanPhamDAO = new LoaiSanPhamDAO(getContext());
        ArrayList<loaiSanPham> list1 = loaiSanPhamDAO.getDSLoaiSP();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (loaiSanPham ls : list1){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", ls.getMaLoai());
            hs.put("tenLoai", ls.getTenLoai());
            listHM.add(hs);
        }
        return listHM;
    }

    private void loadData(){
        list = dao.getDSSanPham();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        SanPhamHAdapter adapter = new SanPhamHAdapter(getContext(),list, getDSLoaiGiay());
        rcv.setAdapter(adapter);
    }
}