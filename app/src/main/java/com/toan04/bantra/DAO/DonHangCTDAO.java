package com.toan04.bantra.DAO;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.DonHangChiTiet;

import java.util.ArrayList;

public class DonHangCTDAO {
    private Dbhelper dbHelper;

    public DonHangCTDAO(Context context) {
        this.dbHelper = new Dbhelper(context);
    }



    public ArrayList<DonHangChiTiet> getChiTietDonHangByMaDonHang(int maDonHang) {
        ArrayList<DonHangChiTiet> listChiTiet = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            String query = "SELECT ChiTietDonHang.maChiTietDonHang, ChiTietDonHang.maSanPham, SanPham.tenSanPham, DonHang.maDonHang, ChiTietDonHang.soLuong, ChiTietDonHang.donGia, ChiTietDonHang.thanhTien, SanPham.anh\n" +
                    "FROM ChiTietDonHang\n" +
                    "INNER JOIN DonHang ON ChiTietDonHang.maDonHang = DonHang.maDonHang\n" +
                    "INNER JOIN SanPham ON ChiTietDonHang.maSanPham = SanPham.maSanPham\n" +
                    "WHERE DonHang.maDonHang = ?";

            Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(maDonHang)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    DonHangChiTiet chiTietDonHang = new DonHangChiTiet();
                    chiTietDonHang.setMaChiTietDonHang(cursor.getInt(0));
                    chiTietDonHang.setMaSanPham(cursor.getInt(1));
                    chiTietDonHang.setTenSanPham(cursor.getString(2));
                    chiTietDonHang.setMaDonHang(cursor.getInt(3));
                    chiTietDonHang.setSoLuong(cursor.getInt(4));
                    chiTietDonHang.setDonGia(cursor.getInt(5));
                    chiTietDonHang.setThanhTien(cursor.getInt(6));
                    chiTietDonHang.setAnhsp(cursor.getString(7));
                    listChiTiet.add(chiTietDonHang);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi", e);
        } finally {
            database.close();
        }
        return listChiTiet;
    }


    public int xoaDonHang(int maDonHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from ChiTietDonHang where maDonHang = ?", new String[]{String.valueOf(maDonHang)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("DonHang", "maDonHang = ?", new String[]{String.valueOf(maDonHang)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean insertDonHangChiTiet(DonHangChiTiet donHangChiTiet) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDonHang", donHangChiTiet.getMaDonHang());
        values.put("maSanPham", donHangChiTiet.getMaSanPham());
        values.put("soLuong", donHangChiTiet.getSoLuong());
        values.put("donGia", donHangChiTiet.getDonGia());
        values.put("thanhTien", donHangChiTiet.getThanhTien());

        long check = sqLiteDatabase.insert("ChiTietDonHang", null, values);
        return check > 0;
    }

    public boolean updateDonHangChiTiet(DonHangChiTiet donHangChiTiet) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maDonHang", donHangChiTiet.getMaDonHang());
        values.put("maSanPham", donHangChiTiet.getMaSanPham());
        values.put("soLuong", donHangChiTiet.getSoLuong());
        values.put("donGia", donHangChiTiet.getDonGia());
        values.put("thanhTien", donHangChiTiet.getThanhTien());

        long check = sqLiteDatabase.update("ChiTietDonHang", values, "machitietdonhang = ?", new String[]{String.valueOf(donHangChiTiet.getMaChiTietDonHang())});
        return check > 0;
    }
}
