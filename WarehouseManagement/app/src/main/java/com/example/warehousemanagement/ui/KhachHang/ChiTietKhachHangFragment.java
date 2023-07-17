package com.example.warehousemanagement.ui.KhachHang;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.warehousemanagement.R;
import com.example.warehousemanagement.dao.DaoKhachHang;
import com.example.warehousemanagement.model.KhachHang;

import org.jetbrains.annotations.NotNull;

public class ChiTietKhachHangFragment extends Fragment {
    private AppCompatActivity appCompatActivity;
    private Drawable drawable;
    private NavController navController;
    private DaoKhachHang dao;
    private String maKH;
    private TextView tvHoTenKH, tvDienThoaiKH, tvDiaChiKH;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chi_tiet_khach_hang, container, false);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawable = getActivity().getDrawable(R.drawable.ic_backspace);
        navController = Navigation.findNavController(view);
        appCompatActivity = (AppCompatActivity) getActivity();
        tvHoTenKH = view.findViewById(R.id.tvHoTenKHShow);
        tvDienThoaiKH = view.findViewById(R.id.tvSoDienThoaiKHShow);
        tvDiaChiKH = view.findViewById(R.id.tvDiaChiKHShow);

        maKH = getArguments().getString("maKH");

        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setTitle("#" + maKH);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(drawable);


        dao = new DaoKhachHang(getActivity());
        dao.open();

        KhachHang khachHang = dao.getMaKH(maKH);

        tvHoTenKH.setText(khachHang.getHoTen());
        tvDienThoaiKH.setText(khachHang.getDienThoai());
        tvDiaChiKH.setText(khachHang.getDiaChi());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            navController.navigate(R.id.action_chitietKH_to_listKH);
            return true;
        } else if (itemId == R.id.menu_edit) {
            Bundle bundle = new Bundle();
            bundle.putString("maKH", maKH);
            navController.navigate(R.id.action_chiteitkh_to_editkh, bundle);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
    }
}