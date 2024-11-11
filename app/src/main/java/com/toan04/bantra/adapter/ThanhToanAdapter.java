package com.toan04.bantra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.toan04.bantra.DAO.DonHangCTDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.DonHangChiTiet;

import java.util.ArrayList;


public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ViewHolder>{
    private ArrayList<DonHangChiTiet> list;
    private Context context;
    private DonHangCTDAO dao;

    public ThanhToanAdapter(ArrayList<DonHangChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangCTDAO(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_confilm_thanh_toan, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenSanPham.setText("Tên sản phẩm: " + list.get(position).getTenSanPham());
        holder.txtMaSanPham.setText("Mã sản phẩm: " + String.valueOf(list.get(position).getMaSanPham()));
        holder.txtMaDonHang.setText("Mã đơn hàng: " + String.valueOf(list.get(position).getMaDonHang()));
        holder.txtSoLuong.setText("Số lượng: " + String.valueOf(list.get(position).getSoLuong()));
        holder.txtDonGia.setText("Giá: " + String.valueOf(list.get(position).getDonGia()));
        holder.txtThanhTien.setText("Thành tiền: " + String.valueOf(list.get(position).getThanhTien()));

        Picasso.get().load(list.get(position).getAnhsp()).into(holder.imgAnhSpthanhtoan);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView txtMaSanPham,txtTenSanPham,txtMaDonHang,txtSoLuong,txtDonGia,txtThanhTien;
        ImageView imgAnhSpthanhtoan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSanPham = itemView.findViewById(R.id.txtMaSanPham1);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham1);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang1);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong1);
            txtDonGia = itemView.findViewById(R.id.txtDonGia1);
            txtThanhTien = itemView.findViewById(R.id.txtThanhTien1);
            imgAnhSpthanhtoan = itemView.findViewById(R.id.imgAnhSpthanhtoan);
        }
    }
}
