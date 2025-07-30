package fpoly.tien25.com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdb.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDanhMuc = "CREATE TABLE DanhMuc (" +
                "maDanhMuc TEXT PRIMARY KEY, " +
                "ten_danh_muc TEXT" +
                ")";
        db.execSQL(createDanhMuc);

        String createSanPham = "CREATE TABLE SanPham (" +
                "maSanPham TEXT PRIMARY KEY, " +
                "tenSanPham TEXT, " +
                "giaSanPham REAL, " +
                "soLuong INTEGER, " +
                "donViTinh TEXT, " +
                "ngayNhap TEXT, " +
                "maDanhMuc TEXT REFERENCES DanhMuc(maDanhMuc)" +
                ")";
        db.execSQL(createSanPham);

        String createNhanVien = "CREATE TABLE NhanVien (" +
                "maNhanVien TEXT PRIMARY KEY, " +
                "tenNhanVien TEXT, " +
                "diaChi TEXT, " +
                "chucVu INTEGER, " +
                "luong REAL, " +
                "matKhau TEXT" +
                ")";
        db.execSQL(createNhanVien);

        // 4. KhachHang
        String createKhachHang = "CREATE TABLE KhachHang (" +
                "maKhachHang TEXT PRIMARY KEY, " +
                "tenKhachHang TEXT, " +
                "diaChi TEXT, " +
                "soDienThoai TEXT, " +
                "email TEXT" +
                ")";
        db.execSQL(createKhachHang);

        // 5. HoaDon
        String createHoaDon = "CREATE TABLE HoaDon (" +
                "maHoaDon TEXT PRIMARY KEY, " +
                "maNhanVien TEXT REFERENCES NhanVien(maNhanVien), " +
                "maKhachHang TEXT REFERENCES KhachHang(maKhachHang), " +
                "ngayLap TEXT, " +
                "tongTien REAL" +
                ")";
        db.execSQL(createHoaDon);

        // 6. HoaDonChiTiet
        String createHDCT = "CREATE TABLE HoaDonChiTiet (" +
                "maHDCT TEXT PRIMARY KEY, " +
                "maHoaDon TEXT REFERENCES HoaDon(maHoaDon), " +
                "maSanPham TEXT REFERENCES SanPham(maSanPham), " +
                "soLuong INTEGER, " +
                "donGia REAL" +
                ")";
        db.execSQL(createHDCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HoaDonChiTiet");
        db.execSQL("DROP TABLE IF EXISTS HoaDon");
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        db.execSQL("DROP TABLE IF EXISTS DanhMuc");
        db.execSQL("DROP TABLE IF EXISTS KhachHang");
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        onCreate(db);

    }
}
