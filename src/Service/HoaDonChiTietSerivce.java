package Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HinhDang;
import model.HoaDon;
import model.HoaDonChiTiet;
import model.MauSac;
import model.SanPham;
import model.SanPhamCT;
import repository.JdbcHelper;

public class HoaDonChiTietSerivce {

    public void insert(HoaDonChiTiet entity) {
        String sql = """
                    INSERT INTO [dbo].[HDCT]
                                ([hoa_don_id]
                                ,[SPCT_id]
                                ,[so_luong]
                                ,[gia])
                          VALUES (?, ?, ?, ?)
                     """;
        JdbcHelper.update(sql,
                entity.getId_HoaDon(),
                entity.getId_SPCT(),
                entity.getSoLuong(),
                entity.getGia()
        );
    }

    public void update(HoaDonChiTiet entity) {
        String sql = """
                     UPDATE [dbo].[HDCT]
                        SET [so_luong] = ?
                      WHERE ID = ?
                     """;
        JdbcHelper.update(sql,
                entity.getSoLuong(),
                entity.getId());
    }

    public void delete(Integer id) {
        String sql = """
                     Delete from HDCT
                     Where ID = ?
                     """;
        JdbcHelper.update(sql, id);
    }

    public HoaDonChiTiet selectById(Integer id) {
        String sql = """
                     SELECT
                            dbo.HDCT.ID,
                            dbo.HoaDon.ma_hoa_don as MaHD,
                            dbo.SanPham.ma_san_pham as MaSP,
                            dbo.SanPham.ten AS TenSP,
                            dbo.MauSac.ten_mau as MauSac,
                            dbo.HinhDang.kieu_dang as KieuDang,
                            dbo.HDCT.gia,
                            dbo.HDCT.so_luong as SoLuong,
                            dbo.HDCT.SPCT_id as ID_SanPhamCT
                        FROM
                            dbo.HDCT
                        INNER JOIN dbo.HoaDon ON dbo.HDCT.hoa_don_id = dbo.HoaDon.ID
                        INNER JOIN dbo.SPCT ON dbo.HDCT.SPCT_id = dbo.SPCT.ID
                        INNER JOIN dbo.SanPham ON dbo.SPCT.san_pham_id = dbo.SanPham.ID
                        INNER JOIN dbo.HinhDang ON dbo.SPCT.hinh_dang_id = dbo.HinhDang.ID
                        INNER JOIN dbo.MauSac ON dbo.SPCT.mau_sac_id = dbo.MauSac.ID
                     WHERE HDCT.ID = ?
                     """;
        List<HoaDonChiTiet> list = this.selectBySql(sql, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public List<HoaDonChiTiet> selectAll() {
        String sql = """
                       SELECT
                            dbo.HDCT.ID,
                            dbo.HoaDon.ma_hoa_don as MaHD,
                            dbo.SanPham.ma_san_pham as MaSP,
                            dbo.SanPham.ten AS TenSP,
                            dbo.MauSac.ten_mau as MauSac,
                            dbo.HinhDang.kieu_dang as KieuDang,
                            dbo.HDCT.gia,
                            dbo.HDCT.so_luong as SoLuong,
                            dbo.HDCT.SPCT_id as ID_SanPhamCT
                        FROM
                            dbo.HDCT
                        INNER JOIN dbo.HoaDon ON dbo.HDCT.hoa_don_id = dbo.HoaDon.ID
                        INNER JOIN dbo.SPCT ON dbo.HDCT.SPCT_id = dbo.SPCT.ID
                        INNER JOIN dbo.SanPham ON dbo.SPCT.san_pham_id = dbo.SanPham.ID
                        INNER JOIN dbo.HinhDang ON dbo.SPCT.hinh_dang_id = dbo.HinhDang.ID
                        INNER JOIN dbo.MauSac ON dbo.SPCT.mau_sac_id = dbo.MauSac.ID
                     """;

        return this.selectBySql(sql);
    }

    public List<HoaDonChiTiet> selectByMaHD(String maHD) {
        String sql = """
                       SELECT
                            dbo.HDCT.ID,
                            dbo.HoaDon.ma_hoa_don as MaHD,
                            dbo.SanPham.ma_san_pham as MaSP,
                            dbo.SanPham.ten AS TenSP,
                            dbo.MauSac.ten_mau as MauSac,
                            dbo.HinhDang.kieu_dang as KieuDang,
                            dbo.HDCT.gia,
                            dbo.HDCT.so_luong as SoLuong,
                            dbo.HDCT.SPCT_id as ID_SanPhamCT
                        FROM
                            dbo.HDCT
                        INNER JOIN dbo.HoaDon ON dbo.HDCT.hoa_don_id = dbo.HoaDon.ID
                        INNER JOIN dbo.SPCT ON dbo.HDCT.SPCT_id = dbo.SPCT.ID
                        INNER JOIN dbo.SanPham ON dbo.SPCT.san_pham_id = dbo.SanPham.ID
                        INNER JOIN dbo.HinhDang ON dbo.SPCT.hinh_dang_id = dbo.HinhDang.ID
                        INNER JOIN dbo.MauSac ON dbo.SPCT.mau_sac_id = dbo.MauSac.ID
                     WHERE dbo.HoaDon.ma_hoa_don LIKE ?
                     """;

        return this.selectBySql(sql, "%" + maHD + "%");
    }

    public List<HoaDonChiTiet> selectByMaSP(String maSP) {
        String sql = """
                     SELECT
                            dbo.HDCT.ID,
                            dbo.HoaDon.ma_hoa_don as MaHD,
                            dbo.SanPham.ma_san_pham as MaSP,
                            dbo.SanPham.ten AS TenSP,
                            dbo.MauSac.ten_mau as MauSac,
                            dbo.HinhDang.kieu_dang as KieuDang,
                            dbo.HDCT.gia,
                            dbo.HDCT.so_luong as SoLuong,
                            dbo.HDCT.SPCT_id as ID_SanPhamCT
                        FROM
                            dbo.HDCT
                        INNER JOIN dbo.HoaDon ON dbo.HDCT.hoa_don_id = dbo.HoaDon.ID
                        INNER JOIN dbo.SPCT ON dbo.HDCT.SPCT_id = dbo.SPCT.ID
                        INNER JOIN dbo.SanPham ON dbo.SPCT.san_pham_id = dbo.SanPham.ID
                        INNER JOIN dbo.HinhDang ON dbo.SPCT.hinh_dang_id = dbo.HinhDang.ID
                        INNER JOIN dbo.MauSac ON dbo.SPCT.mau_sac_id = dbo.MauSac.ID
                     WHERE dbo.SanPham.ma_san_pham like ?
                     """;

        return this.selectBySql(sql, "%" + maSP + "%");
    }

    protected List<HoaDonChiTiet> selectBySql(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();

                hdct.setId(rs.getInt("ID"));
                hdct.setGia(rs.getDouble("gia"));
                hdct.setSoLuong(rs.getInt("SoLuong"));
                hdct.setId_SPCT(rs.getInt("ID_SanPhamCT"));
                hdct.setHd(new HoaDon(rs.getString("MaHD")));
                hdct.setSpct(new SanPhamCT(new SanPham(rs.getString("MaSP"), rs.getString("TenSP"))
                        , new MauSac(rs.getString("MauSac"))
                        , new HinhDang(rs.getString("KieuDang"))));

                list.add(hdct);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
