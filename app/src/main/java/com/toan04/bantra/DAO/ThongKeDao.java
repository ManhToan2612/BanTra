package com.toan04.bantra.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.DonHang;

import java.util.ArrayList;

public class ThongKeDao {
    Dbhelper dbs;
    public ThongKeDao(Context context) {
        dbs = new Dbhelper(context);
    }
    public int tongDoanhThu(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/","");
        ngayKetThuc = ngayKetThuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tongTien) FROM DONHANG WHERE substr(ngaydathang,7) || substr(ngaydathang,4,2) || substr(ngaydathang,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau,ngayKetThuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {

            return 0;
        }
    }
    public int tongDonHang(String ngayBatDau, String ngayKetThuc){
        ngayBatDau = ngayBatDau.replace("/","");
        ngayKetThuc = ngayKetThuc.replace("/","");
        SQLiteDatabase sqLiteDatabase = dbs.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT COUNT(madonhang) FROM DONHANG WHERE substr(ngaydathang,7) || substr(ngaydathang,4,2) || substr(ngaydathang,1,2) BETWEEN ? AND ?", new String[]{ngayBatDau,ngayKetThuc});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }else {
            return 0;
        }
    }
    public ArrayList<DonHang> getDanhSachDonHang(String ngayBatDau, String ngayKetThuc) {
        ngayBatDau = ngayBatDau.replace("/", "");
        ngayKetThuc = ngayKetThuc.replace("/", "");
        SQLiteDatabase sqLiteDatabase = dbs.getReadableDatabase();

        ArrayList<DonHang> danhSachDonHang = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT  DonHang.maDonHang, Admin.maAD, Admin.hoTen, DonHang.ngayDatHang, DonHang.tongTien, DonHang.trangthai FROM DonHang, Admin WHERE DonHang.maAD = Admin.maAD AND substr(ngayDatHang, 7) || substr(ngayDatHang, 4, 2) || substr(ngayDatHang, 1, 2) BETWEEN ? AND ? ORDER BY DonHang.tongTien DESC  ",
                new String[]{ngayBatDau, ngayKetThuc}
        );

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setMaDonHang(cursor.getInt(0));
                donHang.setMaAD(cursor.getString(1));
                donHang.setHoTen(cursor.getString(2));
                donHang.setNgayDatHang(cursor.getString(3));
                donHang.setTongTien(cursor.getInt(4));
                donHang.setTrangthai(cursor.getString(5));

                danhSachDonHang.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return danhSachDonHang;
    }
}
