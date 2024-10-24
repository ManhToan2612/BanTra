package com.toan04.bantra.Dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BanTra";
    private static final int DbVersion = 1;

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

        sqLiteDatabase.execSQL("INSERT INTO SanPham VALUES (1, 'Hồng trà', 30000, 1, 12,'https://athenavshop.com/wp-content/uploads/2022/02/Giay-Hatsune-Miku-High-Top-Odyssey-Sneaker-thumb1.jpg')," +
                "(2, 'Hồng trà', 40000, 2,13,'https://rubystore.com.vn/wp-content/uploads/2021/07/giay-dior-b27-mid-top-sneaker-blue-gray-and-white-sieu-cap.jpg')," +
                "(3, 'Hồng trà', 54444, 2,12,'https://product.hstatic.net/1000284478/product/1062_snw0133_3_5a9a9c18eaa041f68c7ca29565dbef7c_master.jpg')," +
                "(4, 'Hồng trà', 84444, 1,12,'https://shopgiayreplica.com/wp-content/uploads/2023/08/walkndior-high-top-platform-sneaker-deep-blue-dior-oblique-embroidered-cotton-avarta.jpg')," +
                "(5, 'Hồng trà', 1244844, 2,12,'https://igl.prodoc.site/images/auctions13/e7e3d47e932ca3a211a8f6da6ab58d1f-i-img900x900-1648916922sb8g8x21.jpg')," +
                "(6, 'Hồng trà', 3448444, 2,12,'https://admin.thegioigiay.com/upload/product/2023/01/giay-sneaker-nu-basic-de-banh-mi-phoi-mau-tim-size-37-63b8d09c75c5b-07012023085332.jpg')," +
                "(7, 'Hồng trà', 7844844, 1,12,'https://www.vascara.com/uploads/cms_productmedia/2023/October/31/giay-sneaker-co-cao-voi-dem-thoang-khi---snk-0069---mau-den__72268__1698769011.jpg')," +
                "(8, 'Hồng trà', 794444, 2,12,'https://hanoi26sneaker.com/wp-content/uploads/2022/01/Giay-Sneaker-Vans-Checkerboard-Plaid-Tiffany-Blue-Mid-top-chat-luong-scaled.jpg')," +
                "(9, 'Hồng trà', 804844, 2,12,'https://athenavshop.com/wp-content/uploads/2020/10/Giay-Hatsune-Miku-Low-Top-Sneaker-1.jpg')," +
                "(10, 'Hồng trà', 50000, 1, 10,'https://tungluxury.com/wp-content/uploads/2022/11/giay-dior-b27-low-top-sneaker-blue-white-6.jpg')");
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
