package com.toan04.bantra.DAO;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.toan04.bantra.Dbhelper.Dbhelper;
import com.toan04.bantra.model.Admin;

import java.util.ArrayList;

public class AdminDAO {
    private final Dbhelper dbHelper;
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public AdminDAO(Context context) {
        this.dbHelper = new Dbhelper(context);
        sharedPreferences = context.getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
    }

    public ArrayList<Admin> getDSNguoiDung(){
        ArrayList<Admin> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Admin", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Admin(cursor.getString(0),cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public Admin getNguoiDungByMaTaiKhoan(String maAD) {
        Admin admin = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Admin WHERE maAD = ?", new String[]{maAD});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                admin = new Admin();
                admin.setMaAD(cursor.getString(0));
                admin.setHoTen(cursor.getString(1));
                admin.setMatKhau(cursor.getString(2));
                admin.setLoaiTK(cursor.getString(3));
                admin.setAnh(cursor.getString(4));
                admin.setSdt(cursor.getString(5));
                admin.setDiaChi(cursor.getString(6));

            } while (cursor.moveToNext());
        }
        return admin;
    }

    //đăng nhập

    public boolean checkLogin(String maAD,String matKhau) {
        Log.d(TAG, "CheckDangNhap: " + maAD + " - " + matKhau);
        SQLiteDatabase database= dbHelper.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM Admin WHERE maAD = ? AND matKhau = ?", new String[]{maAD, matKhau});
            if (cursor.getCount() > 0){
                cursor.moveToFirst();
                editor = sharedPreferences.edit();
                editor.putString("maAD", cursor.getString(0));
                editor.putString("hoTen", cursor.getString(1));
                editor.putString("matKhau", cursor.getString(2));
                editor.putString("loaiTK", cursor.getString(3));
                editor.putString("anh",cursor.getString(4));
                editor.putString("sdt",cursor.getString(5));
                editor.putString("diaChi",cursor.getString(6));
                editor.commit();
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            Log.e(TAG, "Lỗi kiểm tra đăng nhập", e);
            return false;
        }
    }

    public boolean checkDangKy(Admin admin){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("maAD", admin.getMaAD());
        values.put("hoTen", admin.getHoTen());
        values.put("matKhau", admin.getMatKhau());
        values.put("loaiTK", admin.getLoaiTK());
        values.put("anh",admin.getAnh());
        values.put("sdt",admin.getSdt());
        values.put("diaChi",admin.getDiaChi());
        long result= db.insert("Admin", null, values);
        return result != -1;
    }

    public boolean register(String username, String hoten, String password,String anh,String loaiTK,String sdt, String diachi){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("maAD", username);
        values.put("hoTen", hoten);
        values.put("matKhau", password);
        values.put("loaiTK", loaiTK);
        values.put("anh",anh);
        values.put("sdt",sdt);
        values.put("diaChi",diachi);
        long check = db.insert("Admin", null, values);
        return check != -1;

    }

    public boolean updatePass(String username, String oldPass, String newPass){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Admin where maAD = ? and matKhau = ?", new String[]{username,oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matKhau", newPass);
            long check = db.update("Admin",values,"maAD = ?",new String[]{username});
            if (check == -1){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }

    public boolean updatekhachhang(Admin nguoiDung) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("maAD", nguoiDung.getMaAD());
        values.put("hoTen", nguoiDung.getHoTen());
        values.put("anh", nguoiDung.getAnh());
        values.put("sdt", nguoiDung.getSdt());
        values.put("diaChi", nguoiDung.getDiaChi());

        long check = db.update("Admin", values, "maAD = ?", new String[]{String.valueOf(nguoiDung.getMaAD())});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean tenDangNhapDaTonTai(String tenDangNhap) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM Admin WHERE maAD =?";
        Cursor cursor = db.rawQuery(query, new String[]{tenDangNhap});
        boolean tonTai = cursor.getCount() > 0;
        cursor.close();
        return tonTai;
    }
    public boolean xoaNguoiDung(Admin admin){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("Admin","maAD = ?",new String[]{String.valueOf(admin.getMaAD())});
        return check>0;
    }

    public int delete(String maadmin){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from DonHang where maAD = ?",new String[]{String.valueOf(maadmin)});
        if(cursor.getCount() != 0){
            return -1;
        }

        long check = db.delete("Admin","maAD = ?", new String[]{String.valueOf(maadmin)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }

}
