package com.example.warehousemanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.warehousemanagement.adapter.NhanVienAdapter;
import com.example.warehousemanagement.dao.DaoNhanVien;
import com.example.warehousemanagement.databinding.ActivityMainBinding;
import com.example.warehousemanagement.model.NhanVien;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    List<NhanVien> list;
    View mHeaderView;
    TextView tvUser;
    TextView tvHello;
    CircleImageView imgUser;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NhanVien nhanVien;
    private DaoNhanVien dao;
    private NhanVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.tlbMain);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_hoaDonXuat, R.id.nav_hoaDonNhap, R.id.nav_hang, R.id.nav_sanPham,
                R.id.nav_khachHang, R.id.nav_doanhThu, R.id.nav_topSanPham, R.id.nav_account,
                R.id.nav_info, R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = null;
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        if (navController != null) {
            NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.nav_logout) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mHeaderView = navigationView.getHeaderView(0);
        tvUser = (TextView) mHeaderView.findViewById(R.id.tvMember);
        tvHello = (TextView) mHeaderView.findViewById(R.id.tvHello);
        imgUser = (CircleImageView) mHeaderView.findViewById(R.id.imgUserName);
        list = new ArrayList<>();
        adapter = new NhanVienAdapter(list);
        dao = new DaoNhanVien(this);
        dao.openNV();
        Intent intent = getIntent();
        String user = intent.getStringExtra("user");
        nhanVien = dao.gettaiKhoan(user);
        if (nhanVien.getHinhAnh() == null) {
            String taiK = nhanVien.getTaiKhoan();
            TextDrawable drawable = TextDrawable.builder().beginConfig().width(70).height(70).endConfig().buildRound(taiK.substring(0, 1).toUpperCase(), getRandomColor());
            imgUser.setImageDrawable(drawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getHinhAnh(), 0, nhanVien.getHinhAnh().length);
            imgUser.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 1000, 1000, false));
        }
        tvUser.setText(nhanVien.getHoTen());
        if (user.equalsIgnoreCase("admin")) {
            tvHello.setText("Xin chào Quản trị viên! ");
            navigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
        } else {
            tvHello.setText("Xin chào Member! ");
        }
    }

    public void setAvatar(NhanVien nhanVien) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(nhanVien.getHinhAnh(), 0, nhanVien.getHinhAnh().length);
        imgUser.setImageBitmap(bitmap);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public void setClick(int i) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dao.closeNV();
    }
}