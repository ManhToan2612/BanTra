package com.toan04.bantra.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.toan04.bantra.DAO.GioHangDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.GioHang;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder>{
    private ArrayList<GioHang> list;
    Context context;
    GioHangDAO dao;

    private TotalPriceListener listener;

    public GioHangAdapter(ArrayList<GioHang> list, Context context) {
        this.list = list;
        this.context = context;
        dao = new GioHangDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = list.get(position);
        holder.txtgia.setText(String.valueOf(gioHang.getGiaTien()));
        holder.txtsoluong.setText(String.valueOf(gioHang.getSoLuongMua()));
        holder.txttensp.setText(String.valueOf(gioHang.getTenTra()));
        Picasso.get().load(list.get(position).getAnhSP()).into(holder.AnhSPgiohang);


        holder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gioHang.getSoLuongMua() < gioHang.getSoLuong()){
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() + 1);
                    dao.updateGioHang(gioHang);
                    notifyDataSetChanged();
                    updateTotalPrice();
                }else {
                    Toast.makeText(context, "Không thể mua thêm, số lượng trong kho đã đạt tối đa", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.chkChonSanPham.setOnCheckedChangeListener((compoundButton, b) -> {
            gioHang.setSelected(b);
            updateTotalPrice();

        });

        holder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gioHang.getSoLuongMua() > 1) {
                    gioHang.setSoLuongMua(gioHang.getSoLuongMua() - 1);
                    dao.updateGioHang(gioHang);
                    notifyDataSetChanged();
                    updateTotalPrice();
                }else {
                    removeItem(gioHang);
                }

            }
        });



    }

    public void updateCartList(ArrayList<GioHang> updatedList) {
        list.clear();
        list.addAll(updatedList);
        notifyDataSetChanged();
    }

    private void removeItem(GioHang gioHang) {
        if (dao.deleteGioHang(gioHang)) {
            list.remove(gioHang);
            notifyDataSetChanged();
            updateTotalPrice();
        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateTotalPrice() {
        if (listener != null) {
            int totalAmount = 0;
            for (GioHang gioHang : list) {
                if (gioHang.isSelected()) {
                    totalAmount += gioHang.getSoLuongMua() * gioHang.getGiaTien();
                }
            }
            listener.onTotalPriceUpdated(totalAmount);
        }
    }



    public void setTotalPriceListener(TotalPriceListener listener) {
        this.listener = listener;
    }

    public interface TotalPriceListener {
        void onTotalPriceUpdated(int totalAmount);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttensp,txtsoluong,txtgia;
        ImageButton btntru,btncong;
        ImageView AnhSPgiohang;
        CheckBox chkChonSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensp = itemView.findViewById(R.id.txttensp);
            txtgia = itemView.findViewById(R.id.txtgia);
            txtsoluong = itemView.findViewById(R.id.txtsoluong);
            btncong = itemView.findViewById(R.id.btncong);
            btntru = itemView.findViewById(R.id.btntru);
            chkChonSanPham = itemView.findViewById(R.id.chkChonSanPham);
            AnhSPgiohang = itemView.findViewById(R.id.AnhSPgiohang);

        }
    }
}
