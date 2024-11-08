package com.toan04.bantra;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.toan04.bantra.Fragment.DoiMatKhauFragment;
import com.toan04.bantra.Fragment.GioHangFragment;
import com.toan04.bantra.Fragment.GioiThieuFragment;
import com.toan04.bantra.Fragment.HomeFreagment;
import com.toan04.bantra.Fragment.LichSuFragment;
import com.toan04.bantra.Fragment.LienHeFragment;
import com.toan04.bantra.Fragment.QuanLyDonHangFragment;
import com.toan04.bantra.Fragment.SanPhamFragment;
import com.toan04.bantra.Fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navController;
    NavigationView navigationView;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DrawerLayout dralayout = findViewById(R.id.dralayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.menunav);

        navController = findViewById(R.id.bottomNavigationView);
        setUpNavigation();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.list_24);
        if(savedInstanceState == null ){
//            replay(new HomeFragment() );
            getSupportActionBar().setTitle("Trang Chủ");
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dralayout, toolbar, R.string.open, R.string.close);
        dralayout.addDrawerListener(toggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_gioithieu) {
                    toolbar.setTitle("Giới Thiệu");
                    GioiThieuFragment qlnguoidung = new GioiThieuFragment();
                    replay(qlnguoidung);
                    dralayout.closeDrawer(GravityCompat.START, false);
                } else if (item.getItemId() == R.id.nav_sanpham) {
                    toolbar.setTitle("Sản phẩm");
                    SanPhamFragment sanpham = new SanPhamFragment();
                    replay(sanpham);
                    dralayout.closeDrawer(GravityCompat.START, false);
                }  else if (item.getItemId() == R.id.nav_quanlydonhang) {
                    toolbar.setTitle("Quản lý đơn hàng");
                    QuanLyDonHangFragment qldonhang = new QuanLyDonHangFragment();
                    replay(qldonhang);
                    dralayout.closeDrawer(GravityCompat.START, false);
                } else if (item.getItemId() == R.id.nav_lichsu) {
                    toolbar.setTitle("Lịch sử đơn hàng");
                    LichSuFragment qlthongke = new LichSuFragment();
                    replay(qlthongke);
                    dralayout.closeDrawer(GravityCompat.START, false);
                }else if (item.getItemId() == R.id.nav_lienhe) {
                    toolbar.setTitle("Liên hệ");
                    LienHeFragment lienhe = new LienHeFragment();
                    replay(lienhe);
                    dralayout.closeDrawer(GravityCompat.START, false);
                } else if (item.getItemId() == R.id.nav_doiMatKhau) {
                    toolbar.setTitle("Đổi mật khẩu");
                    DoiMatKhauFragment doimk = new DoiMatKhauFragment();
                    replay(doimk);
                    dralayout.closeDrawer(GravityCompat.START, false);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chăn muốn đăng xuất không!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, DangNhap_H.class));
                            finish();
                        }
                    });

                    dralayout.openDrawer(GravityCompat.START);
                    builder.setNegativeButton("Hủy", null);
                    builder.create().show();

                }

                return false;
            }
        });
    }

    private void setUpNavigation() {
////        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navController.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                SharedPreferences sharedPreferences= getSharedPreferences("USER_FILE", MODE_PRIVATE);
                String loaiTK= sharedPreferences.getString("loaiTK", "");
                if (item.getItemId() == R.id.homeFragment) {
                    replay(new HomeFreagment());
                } else if (item.getItemId() == R.id.gioHangFragment) {
                    if(loaiTK.equals("admin")){
                        Toast.makeText(context, "Không có quyền truy cập", Toast.LENGTH_SHORT).show();
                    } else {
                        replay(new GioHangFragment());
                    }


                } else if (item.getItemId() == R.id.userFragment) {
                    replay(new UserFragment());

                }
                getSupportActionBar().setTitle(item.getTitle());

                return true;
            }
        });



    }

    public void replay(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frglayout, fragment).commit();

    }
}