package com.toan04.bantra.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.SanPham;

import java.util.ArrayList;

public class SanPhamDAO {
    Dbhelper dbHelper;
    public SanPhamDAO(Context context){
        dbHelper= new Dbhelper(context);
    }

    public ArrayList<SanPham> getDSSanPham(){
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0),cursor.getString(1),cursor.getInt(2), cursor.getInt(3),cursor.getInt(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }


    public boolean insert(String tenSanPham, int giaTien, int maloai, int soLuong, String anh){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSanPham",tenSanPham);
        values.put("giaTien",giaTien);
        values.put("maLoai",maloai);
        values.put("soLuong",soLuong);
        values.put("anh", anh);
        long check = db.insert("SanPham",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(int maSanPham, String tenSanPham, int giaTien, int maloai, int soLuong, String anh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenSanPham",tenSanPham);
        values.put("giaTien",giaTien);
        values.put("maLoai",maloai);
        values.put("soLuong",soLuong);
        values.put("anh", anh);
        long check = db.update("SanPham",values,"maSanPham = ?", new String[]{String.valueOf(maSanPham)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }


    public int delete(int maSanPham){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from HoaDon where maSanPham = ?",new String[]{String.valueOf(maSanPham)});
        if(cursor.getCount() != 0){
            return -1;
        }

        long check = db.delete("SanPham","maSanPham = ?", new String[]{String.valueOf(maSanPham)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }


    public boolean updateSlSanPham(int maSanPham, int newQuantity) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soLuong", newQuantity);

        // Đảm bảo rằng điều kiện WHERE sử dụng mã sản phẩm đúng
        String whereClause = "maSanPham = ?";
        String[] whereArgs = {String.valueOf(maSanPham)};

        // Thực hiện cập nhật
        int rowsAffected = database.update("maSanPham", values, whereClause, whereArgs);

        // Trả về true nếu có ít nhất một hàng bị ảnh hưởng
        return rowsAffected > 0;
    }






    private static final String COL_MASP = "maGiay";
    private static final String COL_TENSP = "tenGiay";
    private static final String COL_GIA = "giaTien";
    private static final String COL_MALOAI = "maLoai";
    private static final String COL_SOLUONG = "soLuong";
    private static final String COL_ANH = "anh";

    @SuppressLint("Range")
    public SanPham getSanPhamById(int masanpham) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        SanPham sanPham = null;

        String[] columns = {COL_MASP, COL_TENSP, COL_GIA, COL_MALOAI, COL_SOLUONG, COL_ANH};
        String selection = COL_MASP + "=?";
        String[] selectionArgs = {String.valueOf(masanpham)};

        Cursor cursor = database.query("Giay", columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int maSanPham = cursor.getInt(cursor.getColumnIndex(COL_MASP));
            String tenSanPham = cursor.getString(cursor.getColumnIndex(COL_TENSP));
            int gia = cursor.getInt(cursor.getColumnIndex(COL_GIA));
            int maLoaiSanPham = cursor.getInt(cursor.getColumnIndex(COL_MALOAI));
            int soLuong = cursor.getInt(cursor.getColumnIndex(COL_SOLUONG));
            String anh = cursor.getString(cursor.getColumnIndex(COL_ANH));
            sanPham = new SanPham(maSanPham, tenSanPham, gia, maLoaiSanPham, soLuong, anh);
        }

        cursor.close();
        return sanPham;
    }
}
