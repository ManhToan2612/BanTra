package com.toan04.bantra.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.toan04.bantra.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.toan04.bantra.DAO.AdminDAO;
import com.toan04.bantra.model.Admin;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    TextView txtPTenDangNhap1, txtPHoTen1, soDienThoai1, diaChi1, loaiTaiKhoan1;

    ImageView imgAvatar_Profile;
    Button btnHoSo;
    AdminDAO dao;
    ArrayList<Admin> list = new ArrayList<>();
    Admin admin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        imgAvatar_Profile = view.findViewById(R.id.imgAvatar_Profile);

        txtPTenDangNhap1 = view.findViewById(R.id.txtPTenDangNhap);
        txtPHoTen1 = view.findViewById(R.id.txtPHoTen);
        soDienThoai1 = view.findViewById(R.id.txtPSoDienThoai);
        diaChi1 = view.findViewById(R.id.txtPDiaChi);
        loaiTaiKhoan1 = view.findViewById(R.id.txtPLoaiTaiKhoan);
        btnHoSo = view.findViewById(R.id.btnhoso);
        dao = new AdminDAO(getContext());


        SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String urlAnh = preferences.getString("anh", "");
        Picasso.get().load(urlAnh).into(imgAvatar_Profile);
        String maad = preferences.getString("maAD", "");
        String hoten = preferences.getString("hoTen", "");
        String loaiTk = preferences.getString("loaiTK", "");
        String sdt = preferences.getString("sdt", "");
        String diaChi12 = preferences.getString("diaChi", "");


        soDienThoai1.setText("Số điện thoại: " + sdt);
        diaChi1.setText("Địa chỉ: " + diaChi12);
        loaiTaiKhoan1.setText("Loại tài khoản: " + loaiTk);
        txtPTenDangNhap1.setText("Mã tài khoản: " + maad);
        txtPHoTen1.setText("Họ Tên: " + hoten);
        admin = dao.getNguoiDungByMaTaiKhoan(maad);
        btnHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.item_update_nguoidung, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

//                TextInputLayout in_TenTaiKhoan = view.findViewById(R.id.in_ten_tai_khoan1);
                TextInputLayout in_HoTen = view.findViewById(R.id.in_ho_ten1);
                TextInputLayout in_AnhNguoiDung = view.findViewById(R.id.in_url_user1);
                TextInputLayout in_diaChi = view.findViewById(R.id.in_dc_user1);
                TextInputLayout in_sdt = view.findViewById(R.id.in_sdt_user1);
//                TextInputEditText ed_TenTaiKhoan = view.findViewById(R.id.edt_ten_tai_khoan1);
                TextInputEditText ed_HoTen = view.findViewById(R.id.edt_ho_ten1);
                TextInputEditText ed_AnhNguoiDung = view.findViewById(R.id.edt_url_user1);
                TextInputEditText ed_diaChi = view.findViewById(R.id.edt_dc_user1);
                TextInputEditText ed_sdt = view.findViewById(R.id.edt_sdt_user1);
                Button btn_huy_ND = view.findViewById(R.id.btn_huy_Update_ND);
                Button btnUpdate = view.findViewById(R.id.btn_Update_ND);


//                ed_TenTaiKhoan.setText(admin.getMaAD());
                ed_HoTen.setText(admin.getHoTen());
                ed_AnhNguoiDung.setText(admin.getAnh());
                ed_diaChi.setText(admin.getDiaChi());
                ed_sdt.setText(admin.getSdt());
                btn_huy_ND.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String tentaikhoan = ed_TenTaiKhoan.getText().toString();
                        String hoten = ed_HoTen.getText().toString();
                        String anh = ed_AnhNguoiDung.getText().toString();
                        String diaChi = ed_diaChi.getText().toString();
                        String sdt = ed_sdt.getText().toString();
//                        if ( hoten.isEmpty() || anh.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
//
//
//
//                        }

                        if (hoten.equals("")) {
                            in_HoTen.setError("Vui lòng không để trống Họ Tên");
                            return;
                        } else {
                            in_HoTen.setError(null);
                        }


                        if (ed_AnhNguoiDung.getText().toString().trim().isEmpty()) {
                            in_AnhNguoiDung.setError("Vui lòng nhập link ảnh");
                            return;
                        } else {
                            in_AnhNguoiDung.setError(null);
                        }

                        if (diaChi.equals("")) {
                            in_diaChi.setError("Vui lòng không để trống địa chỉ");
                            return;
                        } else {
                            in_diaChi.setError(null);
                        }


                        if (sdt.equals("")) {
                            in_sdt.setError("Vui lòng không để trống số điện thoại");
                            return;
                        } else if(!isValidSDT(sdt)){
                            in_sdt.setError("Số điện thoại không đúng định dạng!");
                            return;
                        }else {
                            in_sdt.setError(null);
                        }

                            admin.setHoTen(hoten);
                            admin.setAnh(anh);
                            admin.setSdt(sdt);
                            admin.setDiaChi(diaChi);
                            boolean check = dao.updatekhachhang(admin);
                            if (check) {
                                list.clear();
                                list = dao.getDSNguoiDung();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("maAD", admin.getMaAD());
                                editor.putString("hoTen", admin.getHoTen());
                                editor.putString("matKhau", admin.getMatKhau());
                                editor.putString("loaiTK", admin.getLoaiTK());
                                editor.putString("anh", admin.getAnh());
                                editor.putString("sdt", admin.getSdt());
                                editor.putString("diaChi", admin.getDiaChi());
                                editor.apply();
                                String maad1 = preferences.getString("maAD", "");
                                String hoten1 = preferences.getString("hoTen", "");
                                String loaiTk1 = preferences.getString("loaiTK", "");
                                String sdt1 = preferences.getString("sdt", "");
                                String diaChi11 = preferences.getString("diaChi", "");


                                soDienThoai1.setText("Số điện thoại: " + sdt1);
                                diaChi1.setText("Địa chỉ: " + diaChi11);
                                loaiTaiKhoan1.setText("Loại tài khoản: " + loaiTk1);
                                txtPTenDangNhap1.setText("Mã tài khoản: " + maad1);
                                txtPHoTen1.setText("Họ Tên: " + hoten1);
                                dialog.dismiss();
                                Toast.makeText(getContext(), "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Chỉnh sửa thất bại", Toast.LENGTH_SHORT).show();
                            }



                    }
                });



            }
        });



        return view;
    }


    private boolean isValidSDT(String phoneNumber) {
        String regex = "0\\d{9}";
        return phoneNumber.matches(regex);
    }
}