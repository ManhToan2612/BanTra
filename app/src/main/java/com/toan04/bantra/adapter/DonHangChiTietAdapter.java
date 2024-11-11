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

public class DonHangChiTietAdapter extends RecyclerView.Adapter<DonHangChiTietAdapter.ViewHolder>{
    private ArrayList<DonHangChiTiet> list;
    private Context context;
    private DonHangCTDAO dao;

    public DonHangChiTietAdapter(ArrayList<DonHangChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new DonHangCTDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_don_hang_chi_tiet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtDonGia.setText("Giá: "+String.valueOf(list.get(position).getDonGia()));
        holder.txtmaChiTietDon.setText("Mã chi tiết đơn: "+ String.valueOf(list.get(position).getMaChiTietDonHang()));
        holder.txtMaDonHang.setText("Mã đơn hàng: " +String.valueOf(list.get(position).getMaDonHang()));
        holder.txtMaSanPham.setText("Mã sản phẩm: "+String.valueOf(list.get(position).getMaSanPham()));
        holder.txtThanhTien.setText("Thành tiền: "+ String.valueOf(list.get(position).getThanhTien()));
        holder.txtSoLuong.setText("Số lượng: "+String.valueOf(list.get(position).getSoLuong()));
        holder.txttensanpham.setText("Tên sản phẩm: "+list.get(position).getTenSanPham());

        Picasso.get().load(list.get(position).getAnhsp()).into(holder.imgAnhspdonhangchitiet);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttensanpham, txtMaSanPham, txtDonGia, txtMaDonHang, txtThanhTien,txtmaChiTietDon, txtSoLuong ;

        ImageView imgAnhspdonhangchitiet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensanpham = itemView.findViewById(R.id.txttensanpham);
            txtMaSanPham = itemView.findViewById(R.id.txtMaSanPham);
            txtDonGia= itemView.findViewById(R.id.txtDonGia);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtThanhTien = itemView.findViewById(R.id.txtThanhTien);
            txtmaChiTietDon = itemView.findViewById(R.id.txtmaChiTietDon);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgAnhspdonhangchitiet = itemView.findViewById(R.id.imgAnhspdonhangchitiet);


        }
    }
}
