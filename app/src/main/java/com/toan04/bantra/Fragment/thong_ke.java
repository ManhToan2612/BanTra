package com.toan04.bantra.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.toan04.bantra.DAO.ThongKeDao;
import com.toan04.bantra.R;
import com.toan04.bantra.adapter.adapter_ThongKe;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;
import java.util.Calendar;

public class thong_ke extends Fragment {
    TextInputEditText btnlich_bat_dau,btnlich_ket_thuc;
    Button btnthong_ke;

    TextView txtso_luong_don,txttong_tien;
    private ArrayList<DonHang> list = new ArrayList<>();
    private ThongKeDao thongKeDao;
    private adapter_ThongKe adapterThongKe;

    public thong_ke() {

    }



//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);
        btnlich_bat_dau = view.findViewById(R.id.btnlich_bat_dau);
        btnlich_ket_thuc = view.findViewById(R.id.btnlich_ket_thuc);
        btnthong_ke = view.findViewById(R.id.btnthong_ke);
        txtso_luong_don = view.findViewById(R.id.txtso_luong_don);
        txttong_tien = view.findViewById(R.id.txttong_tien);
        ThongKeDao dao = new ThongKeDao(getContext());
        Calendar calendar = Calendar.getInstance();
        // Inflate the layout for this fragment
        RecyclerView rcvThongKe = view.findViewById(R.id.rcvThongKe);
        thongKeDao = new ThongKeDao(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcvThongKe.setLayoutManager(manager);

        //
        btnlich_bat_dau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);

                                // Kiểm tra nếu ngày chọn là ngày hiện tại hoặc sau ngày hiện tại và không quá ngày hiện tại
                                if (!selectedCalendar.after(Calendar.getInstance())) {
                                    String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                                    btnlich_bat_dau.setText(year + "/" + thang + "/" + ngay);
//                                    binding.btnlichBatDau.setText(ngay+"/"+thang+"/"+year);
                                } else {
                                    // Hiển thị thông báo hoặc thực hiện hành động khác nếu người dùng chọn ngày trước hoặc bằng ngày hiện tại.
                                    // Ví dụ: Toast.makeText(getContext(), "Chọn ngày trong khoảng từ ngày hiện tại đến quá khứ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                // Đặt giới hạn cho DatePickerDialog để chỉ cho phép chọn ngày trong khoảng từ ngày hiện tại đến quá khứ
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        //
        btnlich_ket_thuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                Calendar selectedCalendar = Calendar.getInstance();
                                selectedCalendar.set(year, month, dayOfMonth);

                                // Kiểm tra nếu ngày chọn là ngày hiện tại hoặc sau ngày hiện tại và không quá ngày hiện tại
                                if (!selectedCalendar.after(Calendar.getInstance())) {
                                    String ngay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                                    String thang = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);

                                    btnlich_ket_thuc.setText(year + "/" + thang + "/" + ngay);
//                                    binding.btnlichKetThuc.setText(ngay+"/"+thang+"/"+year);
                                } else {
                                    // Hiển thị thông báo hoặc thực hiện hành động khác nếu người dùng chọn ngày trước hoặc bằng ngày hiện tại.
                                    // Ví dụ: Toast.makeText(getContext(), "Chọn ngày trong khoảng từ ngày hiện tại đến quá khứ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                // Đặt giới hạn cho DatePickerDialog để chỉ cho phép chọn ngày trong khoảng từ ngày hiện tại đến quá khứ
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
        btnthong_ke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThongKeDao dao = new ThongKeDao(getContext());
//                "Ngày bắt đầu: "+ ngay + "/" + thang + "/" + year
                String ngaybd = btnlich_bat_dau.getText().toString();
                String ngaykt = btnlich_ket_thuc.getText().toString();

                int tongtien = dao.tongDoanhThu(ngaybd, ngaykt);
                txttong_tien.setText(" Tổng doanh thu:"+tongtien);

                int tongdon = dao.tongDonHang(ngaybd, ngaykt);
                txtso_luong_don.setText("Số lượng đơn hàng đã bán ra: "+tongdon);
                list = thongKeDao.getDanhSachDonHang(ngaybd,ngaykt);
                adapterThongKe = new adapter_ThongKe(list,getContext());
                rcvThongKe.setAdapter(adapterThongKe);
                Toast.makeText(getContext(), "Tong tien"+ tongtien, Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), "Tong don"+tongdon, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}