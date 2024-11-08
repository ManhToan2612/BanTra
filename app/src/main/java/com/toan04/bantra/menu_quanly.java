package com.toan04.bantra;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class menu_quanly extends AppCompatActivity {
    ImageButton btn_back;
    Button btn_dang_xuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_quanly);
        btn_back = findViewById(R.id.btn_back);
        btn_dang_xuat=findViewById(R.id.btn_dang_xuat);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Quay lại màn hình trước đó
                onBackPressed();
            }
        });
        btn_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu_quanly.this, DangNhap_H.class));
            }
        });
    }
}