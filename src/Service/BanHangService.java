package Service;

import java.util.ArrayList;
import model.SanPhamCT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ChatLieu;
import model.DanhMuc;
import model.HinhDang;
import model.MauSac;
import model.PhanLoai;
import model.SanPham;
import model.ThuongHieu;
import repository.JdbcHelper;

public class BanHangService {

    public ArrayList<SanPhamCT> getAll(String sql, Object... args) {
        ArrayList<SanPhamCT> arr = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setId(rs.getInt("id"));
                spct.setSanPham(new SanPham(rs.getString(2), rs.getString(3),
                        new DanhMuc(rs.getString(4)), new ThuongHieu(rs.getString(6)),
                        new ChatLieu(rs.getString(7)), new PhanLoai(rs.getString(5))
                ));
                spct.setGia(rs.getInt("gia"));
                spct.setMauSac(new MauSac(rs.getString("ten_mau")));
                spct.setHinhDang(new HinhDang(rs.getString("kieu_dang")));
                spct.setSoLuong(rs.getInt("so_luong"));
                spct.setTrangThai(rs.getInt("trang_thai"));
                arr.add(spct);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arr;
    }

    public ArrayList<SanPhamCT> selectAll() {
        String sql = "select spct.id , sanpham.ma_san_pham, sanpham.ten, danhmuc.ten_danh_muc, phanloai.phan_loai, thuonghieu.ten_thuong_hieu, chatlieu.chat_lieu,\n"
                + "spct.gia, spct.so_luong, spct.trang_thai, mausac.ten_mau, hinhdang.kieu_dang\n"
                + "from \n"
                + "SPCT inner join MauSac on spct.mau_sac_id = mausac.id\n"
                + "inner join hinhdang on spct.hinh_dang_id = hinhdang.id\n"
                + "inner join sanpham on sanpham.id = SPCT.san_pham_id\n"
                + "inner join danhmuc on danhmuc.id = sanpham.danh_muc_id\n"
                + "inner join PhanLoai on phanloai.id = sanpham.phan_loai_id\n"
                + "inner join thuonghieu on thuonghieu.id = sanpham.thuong_hieu_id\n"
                + "inner join chatlieu on chatlieu.id = sanpham.chat_lieu_id";
        return this.getAll(sql);
    }
    
    
}
