package com.toan04.bantra.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.toan04.bantra.DAO.DonHangCTDAO;
import com.toan04.bantra.DAO.DonHangDAO;
import com.toan04.bantra.DAO.GioHangDAO;
import com.toan04.bantra.DAO.SanPhamDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.GioHangAdapter;
import com.toan04.bantra.model.DonHang;
import com.toan04.bantra.model.DonHangChiTiet;
import com.toan04.bantra.model.GioHang;
import com.toan04.bantra.model.SanPham;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class GioHangFragment extends Fragment implements GioHangAdapter.TotalPriceListener {

    private ArrayList<GioHang> list = new ArrayList<>();
    private GioHangAdapter gioHangAdapter;
    RecyclerView rcvGioHang;
    GioHangDAO gioHangDao;
    private DonHangDAO donHangDao;
    private ArrayList<DonHang> listDonHang = new ArrayList<>();
    Button btnmuahang;
    TextView txttongtien;
    SanPhamDAO SPDAO;

    public GioHangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_gio_hang, container, false);

        btnmuahang = view.findViewById(R.id.btnhomemuahang);
        txttongtien = view.findViewById(R.id.txthometongtien);
        rcvGioHang = view.findViewById(R.id.rcvGioHang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvGioHang.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String maad = sharedPreferences.getString("maAD", "");

        gioHangAdapter = new GioHangAdapter(list,getContext());
        rcvGioHang.setAdapter(gioHangAdapter);
        gioHangDao = new GioHangDAO(getContext());
        SPDAO = new SanPhamDAO(getContext());

        gioHangAdapter.setTotalPriceListener(this);
        DonHangCTDAO chiTietDao =new DonHangCTDAO(getContext());
        donHangDao = new DonHangDAO(getContext());

        list = gioHangDao.getDanhSachGioHangByMaNguoiDung(maad);
        displayCart(list);

        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (GioHang gioHang : list) {
                    if (gioHang.getSoLuong() == 0) {
                        Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenTra() + " đã hết hàng", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                String totalAmountString = txttongtien.getText().toString();
                int totalAmount;
                try {
                    totalAmount = Integer.parseInt(totalAmountString);
                } catch (NumberFormatException e) {
                    totalAmount = 0;
                }

//                int totalAmount = Integer.parseInt(txttongtien.getText().toString());


                LocalDate currentDate = LocalDate.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String ngayHienTai = currentDate.format(formatter);
                String hoTen = sharedPreferences.getString("hoTen","");

//                if (totalAmount > 0) {


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();
                DonHang donHang = new DonHang(maad, hoTen ,ngayHienTai, totalAmount, "Chờ phê duyệt");

                int orderId = donHangDao.insertDonHang(donHang);
                if (orderId != 0){
                    listDonHang.clear();
                    listDonHang.addAll(donHangDao.getDSDonHang());
                    Log.d("tongtien",String.valueOf(txttongtien));
                    if (totalAmount > 0){
                        Log.d("sizeeeeeeeeeee",String.valueOf(list.size()));
                        for (GioHang gioHang : list){
                            if(gioHang.isSelected()){

                                SanPham sanPham = SPDAO.getSanPhamById(gioHang.getMaSanPham());
                                if (sanPham != null){
                                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet(orderId, gioHang.getMaSanPham(), gioHang.getSoLuongMua(), sanPham.getGiaTien(), gioHang.getSoLuongMua() * sanPham.getGiaTien());
                                    chiTietDao.insertDonHangChiTiet(donHangChiTiet);
                                }else {
                                    Toast.makeText(getContext(), "Sản phẩm không tìm thấy trong cơ sở dữ liệu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Vui lòng chọn sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // Cập nhật số lượng sản phẩm sau khi thanh toán thành công
                    for (GioHang gioHang : list) {
                        int newQuantity = gioHang.getSoLuong() - gioHang.getSoLuongMua();
                        if (newQuantity < 0) {
                            Toast.makeText(getContext(), "Sản phẩm " + gioHang.getTenTra() + " không đủ số lượng trong kho", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SPDAO.updateSlSanPham(gioHang.getMaSanPham(), newQuantity);
                    }
                    for (GioHang selected : list) {
                        if (selected.isSelected()) {
                            gioHangDao.deleteGioHang(selected);
                        }
                    }

                    txttongtien.setText(String.valueOf(0));
                    list = gioHangDao.getDSGioHang();
                    gioHangAdapter.updateCartList(list);
                    displayCart(list);

                    Snackbar.make(getView(), "Thanh toán thành công", Snackbar.LENGTH_SHORT).show();
                    Bundle bundle = new Bundle();
                    bundle.putInt("maDonHang", orderId);

//                    ThanhToanFragment frgThanhToan = new ThanhToanFragment();
//                    frgThanhToan.setArguments(bundle);
//                    FragmentManager fragmentManager = getParentFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frglayout, frgThanhToan);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();

                }else {
                    Toast.makeText(getContext(), "Thất bại!", Toast.LENGTH_SHORT).show();
                }
//                }

            }
        });
        return view;

    }

    public void updateGioHangByMaSp(int masp) {
        if (masp > 0) {
            ArrayList<GioHang> updatedCartList = gioHangDao.getDSGioHang();
            list.clear();
            list.addAll(updatedCartList);
            gioHangAdapter.notifyDataSetChanged();
            displayCart(updatedCartList);
        }
    }

    private void displayCart(ArrayList<GioHang> cartList) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvGioHang.setLayoutManager(layoutManager);

        if (gioHangAdapter == null) {
            gioHangAdapter = new GioHangAdapter( cartList,getContext());
            rcvGioHang.setAdapter(gioHangAdapter);

        } else {
            gioHangAdapter.updateCartList(cartList);
            gioHangAdapter.notifyDataSetChanged();
        }
    }

    public void onTotalPriceUpdated(int totalAmount) {
        if (txttongtien != null) {
            txttongtien.setText(String.valueOf(totalAmount));
        }
    }
}