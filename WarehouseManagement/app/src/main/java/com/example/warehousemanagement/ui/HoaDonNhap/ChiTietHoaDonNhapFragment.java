package com.example.warehousemanagement.ui.HoaDonNhap;

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
import com.example.warehousemanagement.dao.DaoCTHD;
import com.example.warehousemanagement.dao.DaoHD;
import com.example.warehousemanagement.dao.DaoNhanVien;
import com.example.warehousemanagement.dao.DaoSanPham;
import com.example.warehousemanagement.model.ChiTietHoaDon;
import com.example.warehousemanagement.model.HoaDon;
import com.example.warehousemanagement.model.NhanVien;
import com.example.warehousemanagement.model.SanPham;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;


public class ChiTietHoaDonNhapFragment extends Fragment {
    private AppCompatActivity appCompatActivity;
    private Drawable drawable;
    private NavController navController;
    private String maHD;
    private DaoCTHD daoCTHD;
    private DaoHD daoHD;
    private DaoNhanVien daoNhanVien;
    private DaoSanPham daoSanPham;
    private TextView tvNV, tvNgay, tvNguoiTao, tvTenSP, tvTTSP, tvSL, tvDonGia, tvThanhTien;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chi_tiet_hoa_don_nhap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkBundle();

        tbCustom(view);

        openData();

        HoaDon hoaDon = daoHD.getMaHD(maHD);
        ChiTietHoaDon chiTietHoaDon = daoCTHD.getMaHD(maHD);
        NhanVien nhanVien = daoNhanVien.getMaNV(hoaDon.getMaNV());
        SanPham sanPham = daoSanPham.getMaSP(chiTietHoaDon.getMaSP());

        tvNV.setText(nhanVien.getHoTen());
        tvNgay.setText(hoaDon.getNgay());
        tvNguoiTao.setText(nhanVien.getHoTen());
        tvTenSP.setText(sanPham.getTenSP());
        switch (sanPham.getTinhTrang()) {
            case 0:
                tvTTSP.setText("Like new 99%");
                break;
            case 1:
                tvTTSP.setText("Mới 100%");
                break;
            default:
                tvTTSP.setText("Cũ");
                break;
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvSL.setText(chiTietHoaDon.getSoLuong() + "");
        tvDonGia.setText(formatter.format(chiTietHoaDon.getDonGia()) + " đ");
        double thanhTien = chiTietHoaDon.getSoLuong() * chiTietHoaDon.getDonGia();

        tvThanhTien.setText(formatter.format(thanhTien) +" đ");
    }

    private void tbCustom(View view) {
        drawable = getActivity().getDrawable(R.drawable.ic_backspace);
        navController = Navigation.findNavController(view);
        appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(drawable);
        appCompatActivity.getSupportActionBar().setTitle(maHD);

        tvNV = view.findViewById(R.id.tvTenNvNhap);
        tvNgay = view.findViewById(R.id.tvNgayTaoNhap);
        tvTenSP = view.findViewById(R.id.tvTenSpNhap);
        tvNguoiTao = view.findViewById(R.id.tvNguoiTaoNhap);
        tvTTSP = view.findViewById(R.id.tvTinhTrangNhap);
        tvSL = view.findViewById(R.id.tvSlNhap);
        tvDonGia = view.findViewById(R.id.tvDonGiaNhap);
        tvThanhTien = view.findViewById(R.id.tvThanhTienNhap);
    }

    public void checkBundle() {
        if (getArguments() != null) {
            maHD = getArguments().getString("maHD");
        }
    }

    private void openData() {
        daoHD = new DaoHD(appCompatActivity);
        daoHD.open();

        daoCTHD = new DaoCTHD(appCompatActivity);
        daoCTHD.open();

        daoSanPham = new DaoSanPham(appCompatActivity);
        daoSanPham.open();

        daoNhanVien = new DaoNhanVien(appCompatActivity);
        daoNhanVien.openNV();
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
            navController.navigate(R.id.ChiTietHDNhap_to_listHDNhap);
            return true;
        } else if (itemId == R.id.menu_edit) {
            Bundle bundle = new Bundle();
            bundle.putString("maHDEdit", maHD);
            navController.navigate(R.id.ChiTietHDNhap_to_editHDNhap, bundle);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        daoHD.close();
        daoCTHD.close();
        daoSanPham.close();
        daoNhanVien.closeNV();
    }
}