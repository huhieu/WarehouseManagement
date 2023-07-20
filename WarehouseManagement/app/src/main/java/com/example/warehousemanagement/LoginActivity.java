package com.example.warehousemanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.warehousemanagement.dao.DaoNhanVien;
import com.example.warehousemanagement.model.NhanVien;


public class LoginActivity extends AppCompatActivity {

    EditText editTextUser, editTextPassword;

    Button btnLogin, btnHuy;

    DaoNhanVien dao;

    CheckBox checkBoxRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUser = findViewById(R.id.edUserName);
        editTextPassword = findViewById(R.id.edPassWorld);

        btnLogin = findViewById(R.id.btnLogin);
        btnHuy = findViewById(R.id.btnReset);

        checkBoxRemember = findViewById(R.id.cbRemember);

        dao = new DaoNhanVien(this);
        dao.openNV();

        //for testing
        if (dao.getUserName("admin") < 0) {
            NhanVien nhanVien = new NhanVien(){
                {
                    setMaNV("Admin");
                    setHoTen("Admin");
                    setDienThoai("0123456789");
                    setTaiKhoan("admin");
                    setMatKhau("123456");
                    setDiaChi("Viet Nam");
                    setNamSinh("1234");
                }
            };
            dao.addNV(nhanVien);
        }

        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        editTextUser.setText(preferences.getString("USER", ""));
        editTextPassword.setText(preferences.getString("PASS", ""));
        checkBoxRemember.setChecked(preferences.getBoolean("REMEMBER", false));
        btnLogin.setOnClickListener(v -> onLoginClick());
        btnHuy.setOnClickListener(v -> onHuyCLick());
    }

    private void onLoginClick(){

    }

    private void onHuyCLick(){
        editTextPassword.setText("");
        editTextUser.setText("");
    }


}