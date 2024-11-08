package com.toan04.bantra.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.toan04.bantra.DAO.DonHangDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.Viewholder> {
    private ArrayList<DonHang> list;
    private DonHangDAO donHangDAO;
    private Context context;


    public DonHangAdapter(ArrayList<DonHang> list, Context context) {
        this.list = list;
        this.context = context;
        donHangDAO= new DonHangDAO(context);
    }

    public interface OnItemClick{
        void onItemClick(int position);
    }
    private OnItemClick mListener;
    public void setOnItemClick(OnItemClick listener){
        mListener = listener;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();
        View view= inflater.inflate(R.layout.item_quan_li_donhang, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        DonHang dh = list.get(position);
        holder.txtmadonhang.setText(String.valueOf(list.get(position).getMaDonHang()));
        holder.txtmanguoidung.setText(list.get(position).getMaAD());
        holder.txthotennguoidung.setText(list.get(position).getHoTen());
        holder.txtngay.setText(list.get(position).getNgayDatHang());
        holder.txttongtien.setText(String.valueOf(list.get(position).getTongTien()));
        holder.txttrangthai.setText(list.get(position).getTrangthai());

        holder.btnchinhsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogUpdateTT(dh);
            }
        });

        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa Dơn Hàng này không!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donHangDAO = new DonHangDAO(context);
                        int check = donHangDAO.xoaDonHang(list.get(holder.getAdapterPosition()).getMaDonHang());
                        switch(check){
                            case 1:
                                list.clear();
                                list.addAll(donHangDAO.getDSDonHang());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa đơn hàng không thành", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được đơn hàng này vì đang còn tồn tại trong Hóa đơn", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }

                    }
                });
                builder.setNegativeButton("NO", null);
                builder.create().show();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(holder.getAdapterPosition());

                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView txtmadonhang, txtmanguoidung, txthotennguoidung, txtngay, txttongtien, txttrangthai, btnchinhsua;
        ImageView btnxoa;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtmadonhang= itemView.findViewById(R.id.txtmadonhang);
            txtmanguoidung= itemView.findViewById(R.id.txtmanguoidung);
            txthotennguoidung= itemView.findViewById(R.id.txthotennguoidung);
            txtngay= itemView.findViewById(R.id.txtngay);
            txttongtien= itemView.findViewById(R.id.txttongtien);
            txttrangthai= itemView.findViewById(R.id.txttrangthai);
            btnchinhsua= itemView.findViewById(R.id.btnchinhsuaTrangThai);
            btnxoa= itemView.findViewById(R.id.btnxoa);
        }
    }
    private void dialogUpdateTT(DonHang dh){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_trangthaidh,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_updonhang = view.findViewById(R.id.in_updonhang);
        TextInputEditText ed_updonhang = view.findViewById(R.id.ed_updonhang);
        Button can= view.findViewById(R.id.btnhuy);
        Button up= view.findViewById(R.id.btnup);

        ed_updonhang.setText(dh.getTrangthai());

        ed_updonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context);
                builder1.setTitle("Lựa chọn trạng thái");
                String[] loai = {"Chờ phê duyệt","Đã phê duyệt","Đang giao hàng","Đã nhận hàng"};

                builder1.setItems(loai, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ed_updonhang.setText(loai[which]);

                    }
                });
                android.app.AlertDialog dialog1 = builder1.create();//tạo hộp thoại
                dialog1.show();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dh.setTrangthai(ed_updonhang.getText().toString());

                String TrangThai = ed_updonhang.getText().toString();

                if(TrangThai.isEmpty()){
                    if(TrangThai.equals("")){
                        in_updonhang.setError("Vui Lòng Nhập Trạng Thái Đơn Hàng");
                    }else{
                        in_updonhang.setError(null);
                    }
                }else{
                    if(donHangDAO.updateDonHang(dh)){
                        list.clear();
                        list.addAll(donHangDAO.getDSDonHang());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
