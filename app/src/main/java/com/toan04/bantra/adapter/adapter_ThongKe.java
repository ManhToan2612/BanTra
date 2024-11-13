package com.toan04.bantra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toan04.bantra.DAO.DonHangDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;

public class adapter_ThongKe extends RecyclerView.Adapter<adapter_ThongKe.ViewHolder> {
    private ArrayList<DonHang> list;
    private Context context;
    public DonHangDAO donHangDAO;
    //
    public adapter_ThongKe(ArrayList<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
        donHangDAO = new DonHangDAO(context);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thongkedonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.txtmadonhang.setText("Mã đơn hàng: " + donHang.getMaDonHang());
        holder.txtmanguoidung.setText("Mã người dùng: " + donHang.getMaAD());
        holder.txthotennguoidung.setText("Họ tên: " + donHang.getHoTen());
        holder.txtngay.setText("Ngày: " + donHang.getNgayDatHang());
        holder.txttongtien.setText("Tổng tiền: " + donHang.getTongTien());
        holder.txttrangthai.setText("Trạng thái: " + donHang.getTrangthai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtmadonhang, txtmanguoidung, txthotennguoidung, txtngay, txttongtien, txttrangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtmadonhang = itemView.findViewById(R.id.txtmadonhangtk);
            txtmanguoidung = itemView.findViewById(R.id.txtmanguoidungtk);
            txthotennguoidung = itemView.findViewById(R.id.txthotennguoidungtk);
            txtngay = itemView.findViewById(R.id.txtngaytk);
            txttongtien = itemView.findViewById(R.id.txttongtientk);
            txttrangthai = itemView.findViewById(R.id.txttrangthaitk);
        }
    }
}
