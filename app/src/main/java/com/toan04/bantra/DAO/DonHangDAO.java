package com.toan04.bantra.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.constraintlayout.helper.widget.MotionEffect;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;

public class DonHangDAO {
    private Dbhelper dbHelper;
    public DonHangDAO(Context context){
        this.dbHelper= new Dbhelper(context);
    }

    public ArrayList<DonHang> getDSDonHang(){
        ArrayList<DonHang> list= new ArrayList<>();
        SQLiteDatabase database= dbHelper.getReadableDatabase();
        try{
            Cursor cursor= database.rawQuery("SELECT DonHang.maDonHang, Admin.maAD, Admin.hoTen, DonHang.ngayDatHang, DonHang.tongTien, DonHang.trangthai" +
                    " from DonHang, Admin where DonHang.maAD = Admin.maAD", null);
            if (cursor.getCount() != 0){
                cursor.moveToFirst();
                do {
                    DonHang donHang= new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaAD(cursor.getString(1));
                    donHang.setHoTen(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));
                    list.add(donHang);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            Log.d(TAG, "Loi", e);
        }
        return list;
    }

    public ArrayList<DonHang> getDonHangByMaTaiKhoan(String maAD) {
        ArrayList<DonHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query="SELECT DonHang.maDonHang, Admin.maAD, Admin.hoTen, DonHang.ngayDatHang, DonHang.tongTien, DonHang.trangthai" +
                    " FROM DonHang JOIN Admin ON DonHang.maAD = Admin.maAD WHERE Admin.maAD = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maAD)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHang donHang = new DonHang();
                    donHang.setMaDonHang(cursor.getInt(0));
                    donHang.setMaAD(cursor.getString(1));
                    donHang.setHoTen(cursor.getString(2));
                    donHang.setNgayDatHang(cursor.getString(3));
                    donHang.setTongTien(cursor.getInt(4));
                    donHang.setTrangthai(cursor.getString(5));
                    list.add(donHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(MotionEffect.TAG, "Lỗi", e);
        }
        return list;
    }





    public int xoaDonHang(int maDonHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ChiTietDonHang where maDonHang = ?",new String[]{String.valueOf(maDonHang)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = db.delete("DonHang","maDonHang = ?",new String[]{String.valueOf(maDonHang)});
        if (check == -1){
            return 0;
        }else {
            return 1;
        }

    }
    public boolean updateDonHang(DonHang donHang) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maAD", donHang.getMaAD());
        values.put("ngayDatHang", donHang.getNgayDatHang());
        values.put("tongTien", donHang.getTongTien());
        values.put("trangthai", donHang.getTrangthai());

        long check = sqLiteDatabase.update("DonHang", values, "maDonHang = ?", new String[]{String.valueOf(donHang.getMaDonHang())});
        return check > 0;
    }
    public int insertDonHang(DonHang donHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maAD",donHang.getMaAD());
        values.put("ngayDatHang",donHang.getNgayDatHang());
        values.put("tongTien", donHang.getTongTien());
        values.put("trangthai", donHang.getTrangthai());

        try {
            long check = db.insert("DonHang",null,values);
            db.close();

            // Kiểm tra xem đơn hàng đã được chèn thành công hay không
            if (check > 0) {
                return (int) check; // Trả về ID của đơn hàng nếu thành công
            } else {
                return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
            }
        } catch (Exception e) {
            Log.e(MotionEffect.TAG, "Lỗi khi chèn đơn hàng", e);
            return -1; // Trả về -1 nếu có lỗi khi chèn đơn hàng
        }


    }
}
