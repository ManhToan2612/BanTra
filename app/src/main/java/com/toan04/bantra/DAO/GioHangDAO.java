package com.toan04.bantra.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.GioHang;

import java.util.ArrayList;

public class GioHangDAO {
    private Dbhelper dbHelper;

    public GioHangDAO(Context context) {
        this.dbHelper = new Dbhelper(context);
    }

    public ArrayList<GioHang> getDSGioHang() {
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
//
            Cursor c = database.rawQuery("SELECT GioHang.maGioHang,GioHang.maAD, SanPham.maSanPham, GioHang.soLuong, SanPham.tenSanPham,SanPham.giaTien, SanPham.soLuong, SanPham.anh" +
                    " from GioHang,SanPham Where GioHang.maSanPham = Giay.maSanPham", null);
            if (c.getCount() != 0) {
                c.moveToFirst();
                do {
                    GioHang gioHang = new GioHang();
                    gioHang.setMaGioHang(c.getInt(0));
                    gioHang.setMaAD(c.getString(1));
                    gioHang.setMaSanPham(c.getInt(2));
                    gioHang.setSoLuongMua(c.getInt(3));
                    gioHang.setTenTra(c.getString(4));
                    gioHang.setGiaTien(c.getInt(5));
                    gioHang.setSoLuong(c.getInt(6));
                    gioHang.setAnhSP(c.getString(7));
                    list.add(gioHang);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi", e);
        }
        return list;
    }

    public ArrayList<GioHang> getDanhSachGioHangByMaNguoiDung(String maAD) {
        ArrayList<GioHang> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            // Thêm điều kiện WHERE cho mã người dùng
            String query =  "SELECT GioHang.maGioHang,GioHang.maAD, SanPham.maSanPham, GioHang.soLuong, SanPham.tenSanPham,SanPham.giaTien, SanPham.soLuong, SanPham.anh" +
                    " from GioHang,SanPham Where GioHang.maSanPham = SanPham.maSanPham AND GioHang.maAD =? ";

            Cursor c = database.rawQuery(query, new String[]{String.valueOf(maAD)});
            if (c.getCount() != 0) {
                c.moveToFirst();
                do {
                    GioHang gioHang = new GioHang();
                    gioHang.setMaGioHang(c.getInt(0));
                    gioHang.setMaAD(c.getString(1));
                    gioHang.setMaSanPham(c.getInt(2));
                    gioHang.setSoLuongMua(c.getInt(3));
                    gioHang.setTenTra(c.getString(4));
                    gioHang.setGiaTien(c.getInt(5));
                    gioHang.setSoLuong(c.getInt(6));
                    gioHang.setAnhSP(c.getString(7));
                    list.add(gioHang);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗiiii", e);
        }
        return list;
    }




    public boolean insertGioHang(GioHang gioHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maAD",gioHang.getMaAD());
        values.put("maSanPham",gioHang.getMaSanPham());
        values.put("soLuong",gioHang.getSoLuongMua());

        long check = db.insert("GioHang",null,values);
        return check>0;
    }
    public boolean updateGioHang(GioHang gioHang) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuong", gioHang.getSoLuongMua());
        int rowsAffected = database.update("GioHang", values, "maGioHang = ?", new String[]{String.valueOf(gioHang.getMaGioHang())});
        return rowsAffected > 0;
    }
    public boolean deleteGioHang(GioHang gioHang){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        long check = sqLiteDatabase.delete("GioHang","maGioHang = ?",new String[]{String.valueOf(gioHang.getMaGioHang())});
        return check >0;
    }

    public GioHang getGioHangByMasp(int masp, String mand) {
        GioHang gioHang = null;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM GioHang WHERE maSanPham = ? AND maAD = ?", new String[]{String.valueOf(masp), String.valueOf(mand)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                gioHang = new GioHang();
                gioHang.setMaGioHang(cursor.getInt(0));
                gioHang.setMaSanPham(cursor.getInt(1));
                gioHang.setMaAD(cursor.getString(2));
                gioHang.setSoLuongMua(cursor.getInt(3));
            }
            cursor.close();
        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
        return gioHang;
    }


}
