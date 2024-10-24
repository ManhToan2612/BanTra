package com.toan04.bantra;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DangNhap_H extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap_h);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView registerText = findViewById(R.id.registerText);
        
        }
    }
