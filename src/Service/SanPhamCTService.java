/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import model.SanPhamCT;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ChatLieu;
import model.DanhMuc;
import model.HinhAnh;
import model.HinhDang;
import model.MauSac;
import model.PhanLoai;
import model.SanPham;
import model.ThuongHieu;

/**
 *
 * @author ledin
 */
public class SanPhamCTService {

    public void insert(SanPhamCT entity) {
        String sql = """
                    INSERT INTO [dbo].[SPCT]
                               ([gia]
                               ,[so_luong]
                               ,[trang_thai]
                               ,[san_pham_id]
                               ,[mau_sac_id]
                               ,[hinh_dang_id]
                               ,[anh_id])
                          VALUES (?, ?, ?, ?, ?, ?, ?)
                     """;

        JdbcHelper.update(sql,
                entity.getGia(),
                entity.getSoLuong(),
                entity.getTrangThai(),
                entity.getId_sanPham(),
                entity.getId_mauSac(),
                entity.getId_HinhDang(),
                entity.getId_Anh());
    }

    public void update(SanPhamCT entity) {
        String sql = """
                     UPDATE [dbo].[SPCT]
                        SET [Gia] = ?
                           ,[so_luong] = ?
                           ,[trang_thai] = ?
                           ,[san_pham_id] = ?
                           ,[mau_sac_id] = ?
                           ,[hinh_dang_id] = ?
                           ,[anh_id] = ?
                      WHERE ID = ?
                     """;

        JdbcHelper.update(sql,
                entity.getGia(),
                entity.getSoLuong(),
                entity.getTrangThai(),
                entity.getId_sanPham(),
                entity.getId_mauSac(),
                entity.getId_HinhDang(),
                entity.getId_Anh(),
                entity.getId()
        );
    }

    public void delete(Integer id) {
        String sql = """
                     DELETE FROM [dbo].[SPCT]
                           WHERE ID = ?
                     """;

        JdbcHelper.update(sql, id);
    }

    public SanPhamCT selectById(Integer id) {
        String sql = """
                    SELECT 
                            spct.ID, 
                            sp.ma_san_pham,
                            sp.ten, 
                            spct.gia, 
                            spct.so_luong,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai, 
                            cl.chat_lieu as ChatLieu, 
                            th.ten_thuong_hieu as ThuongHieu, 
                            ms.ten_mau as MauSac, 
                            hd.kieu_dang as HinhDang, 
                            anh.link as HinhAnh, 
                            spct.trang_thai
                        FROM dbo.SPCT spct
                        INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                        INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                        INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                        INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                        INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                        INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                        INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                        INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                     WHERE spct.ID = ?
                     """;
        List<SanPhamCT> list = this.selectBySql(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<SanPhamCT> selectAll() {
        String sql = """
                    SELECT 
                            spct.ID, 
                            sp.ma_san_pham,
                            sp.ten, 
                            spct.gia, 
                            spct.so_luong,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai, 
                            cl.chat_lieu as ChatLieu, 
                            th.ten_thuong_hieu as ThuongHieu, 
                            ms.ten_mau as MauSac, 
                            hd.kieu_dang as HinhDang, 
                            anh.link as HinhAnh, 
                            spct.trang_thai
                        FROM dbo.SPCT spct
                        INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                        INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                        INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                        INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                        INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                        INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                        INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                        INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                     """;
        return this.selectBySql(sql);
    }

    protected List<SanPhamCT> selectBySql(String sql, Object... args) {
        List<SanPhamCT> list = new ArrayList<>();

        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPhamCT spct = new SanPhamCT();
                spct.setId(rs.getInt("ID"));
                spct.setGia(rs.getInt("gia"));
                spct.setSoLuong(rs.getInt("so_luong"));
                spct.setTrangThai(rs.getInt("trang_thai"));
                spct.setSanPham(new SanPham(rs.getString("ma_san_pham"),
                        rs.getString("ten"),
                        new ThuongHieu(rs.getString("ThuongHieu")),
                        new DanhMuc(rs.getString("DanhMuc")),
                        new ChatLieu(rs.getString("ChatLieu")),
                        new PhanLoai(rs.getString("PhanLoai"))
                ));
                spct.setHinhDang(new HinhDang(rs.getString("HinhDang")));
                spct.setMauSac(new MauSac(rs.getString("MauSac")));
                spct.setHinhAnh(new HinhAnh(rs.getString("HinhAnh")));

                list.add(spct);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public List<SanPhamCT> selectByKeyWord(String keyword) {
        String sql = """
                            SELECT 
                                spct.ID, 
                                sp.ma_san_pham,
                                sp.ten, 
                                spct.gia, 
                                spct.so_luong,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai, 
                                cl.chat_lieu as ChatLieu, 
                                th.ten_thuong_hieu as ThuongHieu, 
                                ms.ten_mau as MauSac, 
                                hd.kieu_dang as HinhDang, 
                                anh.link as HinhAnh, 
                                spct.trang_thai
                            FROM dbo.SPCT spct
                            INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                            INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                            INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                            INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                     WHERE sp.ten LIKE ?
                            OR dm.ten_danh_muc LIKE ? 
                            OR th.ten_thuong_hieu LIKE ?
                            OR sp.ma_san_pham LIKE ?
                     """;
        return this.selectBySql(sql,
                "%" + keyword + "%%",
                "%" + keyword + "%%",
                "%" + keyword + "%%",
                "%" + keyword + "%%");
    }

    public List<SanPhamCT> searchKeyWord(String keyWord, int pages, int limit) {
        String sql = """
                     SELECT * 
                     FROM 
                     (
                         SELECT 
                            spct.ID, 
                            sp.ma_san_pham,
                            sp.ten, 
                            spct.gia, 
                            spct.so_luong,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai, 
                            cl.chat_lieu as ChatLieu, 
                            th.ten_thuong_hieu as ThuongHieu, 
                            ms.ten_mau as MauSac, 
                            hd.kieu_dang as HinhDang, 
                            anh.link as HinhAnh, 
                            spct.trang_thai
                        FROM dbo.SPCT spct
                        INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                        INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                        INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                        INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                        INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                        INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                        INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                        INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                     WHERE sp.ten LIKE ? 
                            OR dm.ten_danh_muc LIKE ? 
                            OR th.ten_thuong_hieu LIKE ?
                            OR sp.ma_san_pham LIKE ?
                     ) AS FilteredResults
                     ORDER BY ID
                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        return this.selectBySql(sql,
                "%" + keyWord + "%%",
                "%" + keyWord + "%%",
                "%" + keyWord + "%%",
                "%" + keyWord + "%%",
                (pages - 1) * limit, limit);
    }

    public List<SanPhamCT> FilterPage(Integer giaMin, Integer giaMax, String mau) {
        String sql = """
                          SELECT 
                            spct.ID, 
                            sp.ma_san_pham,
                            sp.ten, 
                            spct.gia, 
                            spct.so_luong,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai, 
                            cl.chat_lieu as ChatLieu, 
                            th.ten_thuong_hieu as ThuongHieu, 
                            ms.ten_mau as MauSac, 
                            hd.kieu_dang as HinhDang, 
                            anh.link as HinhAnh, 
                            spct.trang_thai
                        FROM dbo.SPCT spct
                            INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                            INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                            INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                            INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                         WHERE 
                              (spct.gia BETWEEN COALESCE(?, spct.gia) AND COALESCE(?, spct.gia))
                              and ms.ten_mau like ?
                     """;
        return this.selectBySql(sql,
                giaMin, giaMax, "%" + mau + "%");
    }

    public List<SanPhamCT> FilterData(Integer giaMin, Integer giaMax, String mau, int pages, int limit) {
        String sql = """
                     SELECT * 
                     FROM 
                     (
                        SELECT 
                            spct.ID, 
                            sp.ma_san_pham,
                            sp.ten, 
                            spct.gia, 
                            spct.so_luong,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai, 
                            cl.chat_lieu as ChatLieu, 
                            th.ten_thuong_hieu as ThuongHieu, 
                            ms.ten_mau as MauSac, 
                            hd.kieu_dang as HinhDang, 
                            anh.link as HinhAnh, 
                            spct.trang_thai
                        FROM dbo.SPCT spct
                            INNER JOIN dbo.SanPham sp ON spct.san_pham_id = sp.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                            INNER JOIN dbo.MauSac ms ON spct.mau_sac_id = ms.ID
                            INNER JOIN dbo.HinhDang hd ON spct.hinh_dang_id = hd.ID
                            INNER JOIN dbo.Anh anh ON spct.anh_id = anh.ID
                         WHERE 
                              spct.gia BETWEEN ISNULL (?, spct.gia) AND  ISNULL (?, spct.gia)
                              and ms.ten_mau like ?
                     ) AS FilteredResults
                     ORDER BY ID
                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        return this.selectBySql(sql,
                giaMin, giaMax, "%" + mau + "%",
                (pages - 1) * limit, limit);
    }
}
