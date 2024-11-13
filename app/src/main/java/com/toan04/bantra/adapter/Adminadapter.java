package com.toan04.bantra.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.toan04.bantra.DAO.AdminDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.Admin;

import java.util.ArrayList;


public class Adminadapter extends RecyclerView.Adapter<Adminadapter.ViewHolder> {
    private ArrayList<Admin> list;
    private Context context;
    AdminDAO addao;

    public Adminadapter(ArrayList<Admin> list, Context context) {
        this.list = list;
        this.context = context;
        addao = new AdminDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_ql_nguoidung, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtma_nguoi_dung.setText("Mã người dùng: " + String.valueOf(list.get(position).getMaAD()));
        holder.txt_Ten_Nguoi_Dung.setText("Tên người dùng: " + list.get(position).getHoTen());
        Admin admin = list.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        holder.btnxoa_ND.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa người dùng này không!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addao = new AdminDAO(context);
                        int check = addao.delete(admin.getMaAD());
                        switch (check) {
                            case 1:
                                //  loadData();
                                list.clear();
                                list.addAll(addao.getDSNguoiDung());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công Người dùng", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành người dùng", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được người dùng này vì đang còn tồn tại trong Hóa đơn", Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtma_nguoi_dung,txt_Ten_Nguoi_Dung;
        ImageButton btnxoa_ND;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtma_nguoi_dung = itemView.findViewById(R.id.txtma_nguoi_dung);
            txt_Ten_Nguoi_Dung = itemView.findViewById(R.id.txt_Ten_Nguoi_Dung);
            btnxoa_ND = itemView.findViewById(R.id.btnxoa_ND);

        }
    }
}
