package com.toan04.bantra.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.loaiSanPham;

import java.util.ArrayList;

public class LoaiSanPhamDAO {
    Dbhelper dbHelper;

    public LoaiSanPhamDAO(Context context) {
        dbHelper = new Dbhelper(context);
    }

    public ArrayList<loaiSanPham> getDSLoaiSP(){
        ArrayList<loaiSanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select g.maGiay, g.tenGiay, g.giaTien, lg.maLoai, lg.tenLoai from Giay g, LoaiGiay lg where g.maLoai = lg.maLoai",null);
        Cursor cursor = db.rawQuery("select * from loaiSanPham",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new loaiSanPham(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String tenLoai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", tenLoai);
        long check = db.insert("loaiSanPham",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public int deleteLSP(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SanPham where maLoai = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("LoaiGiay","maLoai = ?", new String[]{String.valueOf(id)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }

    public boolean update(loaiSanPham loaiSanPham){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenLoai", loaiSanPham.getTenLoai());
        long check = db.update("loaiSanPham",values,"maLoai = ?", new String[]{String.valueOf(loaiSanPham.getMaLoai())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    //get loai san pham by id
    public loaiSanPham getLoaiSanPhamByID(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM loaiSanPham WHERE maLoai = ?", new String[]{Integer.toString(id)});

        if(cursor != null && cursor.moveToFirst()){
            int maLoai = cursor.getInt(cursor.getColumnIndex("maLoai"));
            String tenLoai = cursor.getString(cursor.getColumnIndex("tenLoai"));
            cursor.close();

            return new loaiSanPham(maLoai, tenLoai);
        }

        return null;
    }
}
