package com.toan04.bantra.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.toan04.bantra.DAO.LoaiSanPhamDAO;
import com.toan04.bantra.DAO.SanPhamDAO;
import com.toan04.bantra.R;
import com.toan04.bantra.model.SanPham;
import com.toan04.bantra.model.loaiSanPham;

import java.util.ArrayList;
import java.util.HashMap;

public class SanPhamHAdapter extends RecyclerView.Adapter<SanPhamHAdapter.ViewHoler>{
    private ArrayList<SanPham> list;
    private Context context;
    private ArrayList<HashMap<String, Object>> listHM;
    SanPhamDAO spdao;
    String tensanpham;
    String giasanpham;
    String soLuongSP;
    String anhsp ;

    public SanPhamHAdapter(Context context, ArrayList<SanPham> list, ArrayList<HashMap<String, Object>> listHM ) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        spdao = new SanPhamDAO(context);
    }

    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sanpham, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        SanPham sp = list.get(position);
        LoaiSanPhamDAO loaispdao = new LoaiSanPhamDAO(context);
        loaiSanPham loaisp = loaispdao.getLoaiSanPhamByID(list.get(position).getMaLoai());

        holder.txtmasp.setText("Mã sản phẩm: " + String.valueOf(list.get(position).getMaSanPham()));
        holder.txttensp.setText("Tên sản phẩm: " + list.get(position).getTenSanPham());
        holder.txtgiasp.setText("Giá sản phẩm: " + String.valueOf(list.get(position).getGiaTien()));
        holder.txtmaloaisp.setText("Mã loại sản phẩm: " + loaisp.getMaLoai() + "");
        holder.txtsoluong.setText("Số lượng sản phẩm: " +String.valueOf(sp.getSoLuong()));
        Picasso.get().load(list.get(position).getAnh()).into(holder.imgAnhsp);

        Log.d("slllllllllllll",String.valueOf(list.get(position).getSoLuong()));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateGiay(sp);
            }
        });
        holder.delete_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spdao = new SanPhamDAO(context);
                        int check = spdao.delete(list.get(holder.getAdapterPosition()).getMaSanPham());
                        switch (check) {
                            case 1:
                                list.clear();
                                list = spdao.getDSSanPham();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa thành công sản phẩm", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành sản phẩm", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được sản phẩm này vì đang còn tồn tại trong Hóa đơn", Toast.LENGTH_SHORT).show();
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

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView txtmasp, txttensp, txtgiasp, txtmaloaisp, txtsoluong;
        ImageView delete_sp, imgAnhsp;

        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            txtmasp = itemView.findViewById(R.id.txtma_san_pham);
            txttensp = itemView.findViewById(R.id.txt_ten_san_pham);
            txtgiasp = itemView.findViewById(R.id.txtgia_san_pham);
            txtmaloaisp = itemView.findViewById(R.id.txtma_loai_san_pham2);
            txtsoluong = itemView.findViewById(R.id.txt_so_luong);
            imgAnhsp = itemView.findViewById(R.id.imgAnhsp1);
            delete_sp = itemView.findViewById(R.id.can);
        }
    }


    private void dialogUpdateGiay(SanPham sp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_sanpham, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenSP = view.findViewById(R.id.in_updateTenSP);
        TextInputLayout in_GiaTien = view.findViewById(R.id.in_updateGiaTien);
        TextInputLayout in_SoLuong = view.findViewById(R.id.in_updateSoLuong);
        TextInputLayout in_anh = view.findViewById(R.id.in_updateAnh);
        TextInputEditText ed_TenSanPham = view.findViewById(R.id.ed_updateTenSP);
        TextInputEditText ed_GiaTien = view.findViewById(R.id.ed_updateGiaTien);
        TextInputEditText ed_SoLuong = view.findViewById(R.id.ed_updateSoLuong);
        TextInputEditText ed_anh = view.findViewById(R.id.ed_updateAnh);
        Spinner spnUpadateSP = view.findViewById(R.id.spnSanPham2);
        Button update = view.findViewById(R.id.SP_update);
        Button cancel = view.findViewById(R.id.SP_Cancel2);

        ed_TenSanPham.setText(sp.getTenSanPham());
        ed_GiaTien.setText(String.valueOf(sp.getGiaTien()));
        ed_SoLuong.setText(String.valueOf(sp.getSoLuong()));
        ed_SoLuong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (ed_anh.getText().toString().isEmpty()){
                        in_anh.setError("Vui lòng nhập link ảnh");
                    }else {
                        in_anh.setError(null);
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
                if (charSequence.length() == 0) {
                    in_SoLuong.setError("Vui lòng không để trống số lượng sản phẩm");
                    return;
                } else {
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
                if (charSequence.length() == 0) {
                    in_TenSP.setError("Vui lòng không để trống tên sản phẩm");
                    return;
                } else {
                    in_TenSP.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        ed_GiaTien.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    in_GiaTien.setError("Vui lòng không để trống giá tiền");
                } else {
                    in_GiaTien.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnUpadateSP.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;

        for (HashMap<String, Object> item : listHM) {
            if ((int) item.get("maLoai") == sp.getMaLoai()) {
                position = index;
                break;  // Thêm break để thoát khỏi vòng lặp khi tìm thấy vị trí
            }
            index++;
        }
        spnUpadateSP.setSelection(position);



        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tensanpham = ed_TenSanPham.getText().toString();
                giasanpham = ed_GiaTien.getText().toString();
                soLuongSP = ed_SoLuong.getText().toString();
                anhsp = ed_anh.getText().toString();

                HashMap<String, Object> hs = (HashMap<String, Object>) spnUpadateSP.getSelectedItem();
                int maloaisp = (int) hs.get("maLoai");

                if (tensanpham.isEmpty() || giasanpham.isEmpty() || soLuongSP.isEmpty() || anhsp.isEmpty()) {
                    if (tensanpham.equals("")) {
                        ed_TenSanPham.setError("Vui lòng không để trống tên sản phẩm");
                    } else {
                        ed_TenSanPham.setError(null);
                    }
                    if (giasanpham.equals("")) {
                        ed_GiaTien.setError("Vui lòng không để trống giá sản phẩm");
                    } else {
                        ed_GiaTien.setError(null);
                    }
                    if (soLuongSP.equals("")) {
                        ed_SoLuong.setError("Vui lòng không để trống số lượng sản phẩm");
                    } else {
                        ed_SoLuong.setError(null);
                    }
                    if (anhsp.equals("")) {
                        ed_anh.setError("Vui lòng không để trống số link anh sản phẩm");
                        return;
                    } else {
                        ed_anh.setError(null);
                    }


                } else {
                    try {
                        int tien = Integer.parseInt(giasanpham);
                        int soluong = Integer.parseInt(soLuongSP);

                        if (tien <= 0) {
                            ed_GiaTien.setError("Giá sản phẩm phải lớn hơn 0");
                        } else if (soluong <= 0) {
                            ed_SoLuong.setError("Số lượng sản phẩm phải lớn hơn 0");
                        } else {
                            ed_GiaTien.setError(null);
                            ed_SoLuong.setError(null);

                            boolean check = spdao.update(sp.getMaSanPham(), tensanpham, tien, maloaisp, soluong, anhsp);

                            if (check) {
                                list.clear();
                                list.addAll(spdao.getDSSanPham());
                                notifyDataSetChanged();
                                Toast.makeText(context, "Cập nhật thành công sản phẩm", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(context, "Cập nhật không thành công sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (NumberFormatException e) {
                        ed_GiaTien.setError("Giá tiền sản phẩm và số lượng sản phẩm phải là số");
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

    //    private void loadData(){
//        list.clear();
//        list = dao.getDSSanPham();
//        notifyDataSetChanged();
//    }
    private void validTenSp(){
        if (tensanpham.isEmpty()){

        }
    }
}
