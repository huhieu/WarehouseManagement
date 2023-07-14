package com.example.warehousemanagement.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.warehousemanagement.database.DbHelper;
import com.example.warehousemanagement.model.NhanVien;

import java.util.ArrayList;
import java.util.List;

public class DaoNhanVien {
    SQLiteDatabase database;
    DbHelper dbHelper;

    public DaoNhanVien(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void openNV() {
        database = dbHelper.getWritableDatabase();
    }

    public void closeNV() {
        dbHelper.close();
    }

    public long addNV(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.TB_COL_ID, nhanVien.getMaNV());
        values.put(NhanVien.TB_COL_NAME, nhanVien.getHoTen());
        values.put(NhanVien.TB_COL_PHONE, nhanVien.getDienThoai());
        values.put(NhanVien.TB_COL_USER, nhanVien.getTaiKhoan());
        values.put(NhanVien.TB_COL_LOCATION, nhanVien.getDiaChi());
        values.put(NhanVien.TB_COL_DATE, nhanVien.getNamSinh());
        values.put(NhanVien.TB_COL_PASS, nhanVien.getMatKhau());
        values.put(NhanVien.TB_COL_IMAGE, nhanVien.getHinhAnh());
        return database.insert(NhanVien.TB_NAME, null, values);
    }

    public int updateNV(NhanVien nhanVien, String maNVold) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.TB_COL_ID, nhanVien.getMaNV());
        values.put(NhanVien.TB_COL_NAME, nhanVien.getHoTen());
        values.put(NhanVien.TB_COL_PHONE, nhanVien.getDienThoai());
        values.put(NhanVien.TB_COL_USER, nhanVien.getTaiKhoan());
        values.put(NhanVien.TB_COL_LOCATION, nhanVien.getDiaChi());
        values.put(NhanVien.TB_COL_DATE, nhanVien.getNamSinh());
        values.put(NhanVien.TB_COL_PASS, nhanVien.getMatKhau());
        values.put(NhanVien.TB_COL_IMAGE, nhanVien.getHinhAnh());
        return database.update(NhanVien.TB_NAME, values, "maNV=?", new String[]{maNVold});
    }

    public int deleteNV(NhanVien nhanVien) {
        return database.delete(NhanVien.TB_NAME, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public List<NhanVien> getAll() {
        String sql = "SELECT * FROM NhanVien WHERE taiKhoan != 'admin' ";
        List<NhanVien> list = getdata(sql);
        return list;
    }

    public NhanVien getMaNV(String maNV) {
        String sql = "SELECT * FROM NhanVien WHERE maNV=?";
        List<NhanVien> list = getdata(sql, maNV);
        return list.get(0);
    }

    public List<NhanVien> getAllSXTen() {
        String sql = "SELECT * FROM NhanVien ORDER BY hoTen ASC";
        List<NhanVien> list = getdata(sql);
        return list;
    }

    public List<NhanVien> getAllSXMa() {
        String sql = "SELECT * FROM NhanVien ORDER BY maNV ASC";
        List<NhanVien> list = getdata(sql);
        return list;
    }

    public List<NhanVien> getAllSXTK() {
        String sql = "SELECT * FROM NhanVien ORDER BY taiKhoan ASC";
        List<NhanVien> list = getdata(sql);
        return list;
    }

    public NhanVien gettaiKhoan(String maTK) {
        String sql = "SELECT * FROM NhanVien WHERE taiKhoan=?";
        List<NhanVien> list = getdata(sql, maTK);
        return list.get(0);
    }
    public int checkMaNV(String maNV) {
        String dl = "SELECT * FROM NhanVien WHERE  maNV=?";
        List<NhanVien> list = getdata(dl, maNV);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
    public int getUserName(String user) {
        String dl = "SELECT * FROM NhanVien WHERE  taiKhoan=?";
        List<NhanVien> list = getdata(dl, user);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getlogin(String user, String pass) {
        String dl = "SELECT * FROM NhanVien WHERE taiKhoan=? AND matKhau=?";
        List<NhanVien> list = getdata(dl, user, pass);
        if (list.size() == 0) {
            return -1;
        } else {
            return 1;
        }
    }
    public int changePassword(NhanVien nhanVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NhanVien.TB_COL_PASS,nhanVien.getMatKhau());
        return database.update(NhanVien.TB_NAME,contentValues,"maNV=?",new String[]{nhanVien.getMaNV()});
    }

    @SuppressLint("Range")
    public List<NhanVien> getdata(String sql, String... args) {
        List<NhanVien> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setMaNV(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_ID)));
            nhanVien.setHoTen(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_NAME)));
            nhanVien.setDienThoai(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_PHONE)));
            nhanVien.setTaiKhoan(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_USER)));
            nhanVien.setDiaChi(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_LOCATION)));
            nhanVien.setMatKhau(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_PASS)));
            nhanVien.setNamSinh(cursor.getString(cursor.getColumnIndex(NhanVien.TB_COL_DATE)));
            nhanVien.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(NhanVien.TB_COL_IMAGE)));
            list.add(nhanVien);
        }
        return list;
    }

    public void testDaoNhanVien() {

        // Tạo một đối tượng NhanVien để thêm vào cơ sở dữ liệu
        NhanVien nhanVien = new NhanVien();
        nhanVien.setMaNV("NV001");
        nhanVien.setHoTen("John Doe");
        nhanVien.setDienThoai("0123456789");
        nhanVien.setTaiKhoan("john.doe");
        nhanVien.setDiaChi("123 ABC Street");
        nhanVien.setMatKhau("123456");
        nhanVien.setNamSinh("1990-01-01");
        nhanVien.setHinhAnh(null); // Thay thế null bằng một mảng byte[] chứa dữ liệu hình ảnh

        // Thêm nhân viên vào cơ sở dữ liệu
//        long addResult = DaoNhanVien.this.addNV(nhanVien);
        List<NhanVien> list=DaoNhanVien.this.getAll();
        for (NhanVien nv:list
             ) {
            System.out.println(nv.getDiaChi());
        }
    }


}

