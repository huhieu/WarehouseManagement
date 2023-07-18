package com.example.warehousemanagement.ui.Sanpham;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.warehousemanagement.R;
import com.example.warehousemanagement.dao.DaoHang;
import com.example.warehousemanagement.dao.DaoSanPham;
import com.example.warehousemanagement.dao.DaoThuocTinhSanPham;
import com.example.warehousemanagement.databinding.FragmentChiTietSanPhamBinding;
import com.example.warehousemanagement.model.SanPham;
import com.example.warehousemanagement.model.ThuocTinhSanPham;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.Random;

public class ChiTietSanPhamFragment extends Fragment {
    private AppCompatActivity appCompatActivity;
    private Drawable drawable;
    private NavController navController;
    private String maSP;
    private DaoSanPham dao;
    private DaoHang daoHang;
    private DaoThuocTinhSanPham daoTT;
    private FragmentChiTietSanPhamBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChiTietSanPhamBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkBundle();

        tbCustom(view);

        dao = new DaoSanPham(appCompatActivity);
        dao.open();

        daoHang = new DaoHang(appCompatActivity);
        daoHang.open();

        daoTT = new DaoThuocTinhSanPham(appCompatActivity);
        daoTT.open();

        if (maSP != null) {
            SanPham sanPham = dao.getMaSP(maSP);
            showPhanLoai(sanPham);
            showSanPham(sanPham);
        }

    }

    public void showSanPham(SanPham sanPham) {
        if (sanPham.getHinhAnh() == null) {
            String tenSP = sanPham.getTenSP();
            TextDrawable textDrawable = TextDrawable.builder().beginConfig().width(48).height(48).endConfig().buildRect(tenSP.substring(0, 1).toUpperCase(), getRandomColor());
            binding.imgSanPhamDeltail.setImageDrawable(textDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(sanPham.getHinhAnh(), 0, sanPham.getHinhAnh().length);
            binding.imgSanPhamDeltail.setImageBitmap(bitmap);
        }
        binding.tvTenSPDeltail.setText(sanPham.getTenSP());
        binding.tvHangSPDeltail.setText(daoHang.getMaHang(sanPham.getMaHang()).getTenHang());

        switch (sanPham.getTinhTrang()) {
            case 0:
                binding.tvTinhTrangSPDeltail.setText("Like new 99%");
                break;
            case 1:
                binding.tvTinhTrangSPDeltail.setText("Mới 100%");
                break;
            default:
                binding.tvTinhTrangSPDeltail.setText("Cũ");
                break;
        }

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        binding.tvGiaTienSPDeltail.setText(formatter.format(sanPham.getGiaTien()) + " đ");

        if (sanPham.getTrangThai() == 0) {
            binding.tvTrangThaiSPDeltail.setText("Chưa lưu kho");
        } else {
            binding.tvTrangThaiSPDeltail.setText("Đã lưu kho");
        }

        binding.tvMotaSPDeltail.setText(sanPham.getMoTa());

        ThuocTinhSanPham thuocTinhSP = daoTT.getMaSP(sanPham.getMaSP());

        if (sanPham.getPhanLoai() == 0) {
            binding.tvLoaiSPDeltail.setText("Điện thoại");
            binding.tvBoNhoSPDeltail.setText(thuocTinhSP.getBoNho());
            binding.tvRAMSPDeltail.setText(thuocTinhSP.getRAM());
            binding.tvChipSPDeltail.setText(thuocTinhSP.getChipSet());
            binding.tvOSSPDeltail.setText(thuocTinhSP.getHeDieuHanh());
            binding.tvManHinhSPDeltail.setText(thuocTinhSP.getManHinh());
            binding.tvBatterySPDeltail.setText(thuocTinhSP.getDungLuongPin());
            binding.tvCongSacSPDeltail.setText(thuocTinhSP.getCongSac());
        } else {
            binding.tvLoaiPKSPDeltail.setText(thuocTinhSP.getLoaiPhuKien());
            binding.tvLoaiSPDeltail.setText("Phụ kiện");
        }
    }

    public void showPhanLoai(SanPham sanPham) {

        if (sanPham.getPhanLoai() > 0) {
            binding.lnlBoNhoSPDeltail.setVisibility(View.GONE);
            binding.lnlRAMSPDeltail.setVisibility(View.GONE);
            binding.lnlChipSPDeltail.setVisibility(View.GONE);
            binding.lnlOSSPDeltail.setVisibility(View.GONE);
            binding.lnlManHinhSPDeltail.setVisibility(View.GONE);
            binding.lnlBatterySPDeltail.setVisibility(View.GONE);
            binding.lnlCongSacSPDeltail.setVisibility(View.GONE);

        } else {
            binding.lnlLoaiPKSPDeltail.setVisibility(View.GONE);
        }
    }

    private void tbCustom(@NotNull View view) {
        drawable = getActivity().getDrawable(R.drawable.ic_backspace);
        navController = Navigation.findNavController(view);
        appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appCompatActivity.getSupportActionBar().setHomeAsUpIndicator(drawable);
        appCompatActivity.getSupportActionBar().setTitle(maSP);

    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public void checkBundle() {
        if (getArguments() != null) {
            maSP = getArguments().getString("maSP");
        }
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
            navController.navigate(R.id.chitetSP_to_listSP);
            return true;
        } else if (itemId == R.id.menu_edit) {
            Bundle bundle = new Bundle();
            bundle.putString("maSPChange", maSP);
            navController.navigate(R.id.chitetSP_to_editSP, bundle);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dao.close();
        daoHang.close();
        daoTT.close();
    }
}