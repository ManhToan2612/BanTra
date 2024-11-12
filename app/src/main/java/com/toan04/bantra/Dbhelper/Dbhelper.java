package com.toan04.bantra.Dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BanTra";
    private static final int DbVersion = 3;

    public Dbhelper(@Nullable Context context) {
        super(context, DB_NAME,null, DbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Tao bang Admin
        String  tb_Admin = ("CREATE TABLE Admin (" +
                "maAD TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL," +
                "loaiTK TEXT NOT NULL," +
                "anh TEXT," +
                "sdt TEXT," +
                "diaChi TEXT)");
        sqLiteDatabase.execSQL(tb_Admin);

        // Bang loai san pham
        String tb_loaiSanPham= "CREATE TABLE loaiSanPham(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULL)";
        sqLiteDatabase.execSQL(tb_loaiSanPham);

        // Bang san pham
        String tb_SanPham= "CREATE TABLE SanPham(" +
                "maSanPham INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSanPham TEXT NOT NULL," +
                "giaTien INTEGER NOT NULL," +
                "maLoai INTEGER REFERENCES loaiSanPham(maLoai)," +
                "soLuong INTEGER NOT NULL," +
                "anh TEXT NOT NULL)";
        sqLiteDatabase.execSQL(tb_SanPham);

        // Bảng giỏ hàng
        String tb_gioHang = "CREATE TABLE GioHang(" +
                "maGioHang integer primary key autoincrement, " +
                "maAD TEXT REFERENCES Admin(maAD)," +
                "maSanPham integer REFERENCES SanPham(maSanPham)," +
                "soLuong integer not null)";
        sqLiteDatabase.execSQL(tb_gioHang);

        // Bang don hang
        String tb_donHang= "CREATE TABLE DonHang(" +
                "maDonHang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maAD TEXT REFERENCES Admin(maAD)," +
                "ngayDatHang TEXT NOT NULL," +
                "tongTien INTEGER NOT NULL," +
                "trangthai TEXT NOT NULL)";
        sqLiteDatabase.execSQL(tb_donHang);

        // Bảng chi tiết đơn hàng
        String chiTietDonHang = "CREATE TABLE ChiTietDonHang(" +
                "maChiTietDonHang INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maDonHang INTEGER REFERENCES DonHang(maDonHang)," +
                "maSanPham INTEGER REFERENCES SanPham(maSanPham)," +
                "soLuong INTEGER NOT NULL," +
                "donGia INTEGER NOT NULL," +
                "thanhTien INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(chiTietDonHang);

        //insert date
        sqLiteDatabase.execSQL("INSERT INTO Admin VALUES ('admin1','Nguyễn Văn Admin','admin', 'admin','https://www.shareicon.net/data/128x128/2016/08/05/806962_user_512x512.png','0967110342','Hà Nội')," +
                "('admin12','Nguyễn Văn Admin','admin12', 'khachhang','https://reputationprotectiononline.com/wp-content/uploads/2022/04/78-786207_user-avatar-png-user-avatar-icon-png-transparent-287x300.png','028383','Hà Tây'), " +
                "('khachhang1','Nguyen Văn A ','123456', 'khachhang','https://www.google.com/imgres?imgurl=https%3A%2F%2Fps.w.org%2Fuser-avatar-reloaded%2Fassets%2Ficon-256x256.png%3Frev%3D2540745&tbnid=4tCxPvBodOnWbM&vet=12ahUKEwin0prGtPCCAxUvavUHHQnwDoIQMygAegQIARBT..i&imgrefurl=https%3A%2F%2Ffa.wordpress.org%2Fplugins%2Fuser-avatar-reloaded%2F&docid=7XY1G-DhNW4M4M&w=257&h=257&q=avt%20user&ved=2ahUKEwin0prGtPCCAxUvavUHHQnwDoIQMygAegQIARBT','982738489','TP Hồ Chí Minh')");

        sqLiteDatabase.execSQL("INSERT INTO loaiSanPham VALUES ('1', 'Tra xanh')," +
                "('2', 'Tra kho')");

        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES (1, 'Hồng trà', 30000, 1, 12,'https://bizweb.dktcdn.net/thumb/grande/100/025/663/files/hong-tra-oha-tra-sua-01.jpg?v=1610389060750')," +
                "(2, 'Trà khô', 40000, 2,13,'https://goodprice.vn/files/common/la-tra-xanh-say-kho-b1fs5.jpg')," +
                "(3, 'Trà xanh', 54444, 2,12,'https://danhtra.com/wp-content/uploads/2019/04/56769858_6146052593024_5529686684687400960_n.jpg')," +
                "(4, 'Trà ô long', 84444, 1,12,'https://huyenhashop.com/wp-content/uploads/2021/01/tra-o-long-01.jpg')," +
                "(5, 'Bạch trà', 1244844, 2,12,'https://file.hstatic.net/1000135323/file/bach-tra-1_48227381ddf3408387c6e78a605ec1c3_1024x1024.jpg')," +
                "(6, 'Hoàng trà', 3448444, 2,12,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJwNbcYdsFIAQp1H5k1ibPMcRchpW6IXDphg&s')," +
                "(7, 'Trà đen', 7844844, 1,12,'https://www.tita.art/wp-content/uploads/2023/06/cac-loai-tra-den.jpg')," +
                "(8, 'Trà shan tuyết', 794444, 2,12,'https://www.traviet.com/wp-content/uploads/2015/06/tra-tuyet-zoom1-800x800.jpg')," +
                "(9, 'Trà nhài', 804844, 2,12,'https://nguyenlieuantoan.com/assets/upload/products/1645086311_tra-nhai-a.jpg?v=0.0.3.1')," +
                "(10, 'Trà bá tuớc', 50000, 1, 10,'https://cdn.tgdd.vn/2021/11/CookDish/tra-earl-grey-la-gi-cong-dung-cua-tra-earl-grey-n-avt-1200x676.jpg')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists Admin");
        sqLiteDatabase.execSQL("drop table if exists HoaDon");
        sqLiteDatabase.execSQL("drop table if exists GioHang");
        sqLiteDatabase.execSQL("drop table if exists ChiTietDonHang");
        sqLiteDatabase.execSQL("drop table if exists DonHang");
        sqLiteDatabase.execSQL("drop table if exists SanPham");
        sqLiteDatabase.execSQL("drop table if exists loaiSanPham");

        onCreate(sqLiteDatabase);
    }
}
