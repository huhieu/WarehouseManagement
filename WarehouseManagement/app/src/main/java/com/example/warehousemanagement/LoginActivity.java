package com.example.warehousemanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        String user = editTextUser.getText().toString();
        String pass = editTextPassword.getText().toString();
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            boolean isExist = dao.getlogin(user, pass) > 0;
            if (isExist) {
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("USER", user);
                editor.putString("PASS", pass);
                editor.putBoolean("REMEMBER", checkBoxRemember.isChecked());
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onHuyCLick(){
        editTextPassword.setText("");
        editTextUser.setText("");
    }


}