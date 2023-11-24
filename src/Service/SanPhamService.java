/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import model.SanPham;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.ChatLieu;
import model.DanhMuc;
import model.PhanLoai;
import model.ThuongHieu;

/**
 *
 * @author ledin
 */
public class SanPhamService {

    public void insert(SanPham entity) {
        String sql = """
                        INSERT INTO [dbo].[SanPham]
                                              ([ma_san_pham]
                                              ,[ten]
                                              ,[ngay_tao]
                                              ,[ngay_sua]
                                              ,[danh_muc_id]
                                              ,[thuong_hieu_id]
                                              ,[phan_loai_id]
                                              ,[chat_lieu_id])
                             VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                        """;

        JdbcHelper.update(sql,
                entity.getMa(),
                entity.getTen(),
                entity.getNgayThem(),
                entity.getNgaySua(),
                entity.getId_dm(),
                entity.getId_th(),
                entity.getId_pl(),
                entity.getId_cl());
    }

    public void update(SanPham entity) {
        String sql = """
                        UPDATE [dbo].[SanPham]
                                 SET [ma_san_pham] = ?
                                    ,[ten] = ?
                                    ,[ngay_sua] = ?
                                    ,[danh_muc_id] = ?
                                    ,[thuong_hieu_id] = ?
                                    ,[phan_loai_id] = ?
                                    ,[chat_lieu_id] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getMa(),
                entity.getTen(),
                entity.getNgaySua(),
                entity.getId_dm(),
                entity.getId_th(),
                entity.getId_pl(),
                entity.getId_cl(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[SanPham]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public SanPham selectById(Integer id) {
        String selectById = """
                        SELECT
                            sp.ID,
                            sp.ma_san_pham,
                            sp.ten,
                            sp.ngay_tao,
                            sp.ngay_sua,
                            th.ten_thuong_hieu as ThuongHieu,
                            dm.ten_danh_muc as DanhMuc,
                            pl.phan_loai as PhanLoai,
                            cl.chat_lieu as ChatLieu
                        FROM
                              dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                         WHERE  sp.ID = ?
                        """;
        List<SanPham> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setId(rs.getInt("ID"));
                sp.setMa(rs.getString("ma_san_pham"));
                sp.setTen(rs.getString("ten"));
                sp.setNgayThem(rs.getDate("ngay_tao"));
                sp.setNgaySua(rs.getDate("ngay_sua"));
                sp.setThuongHieu(new ThuongHieu(rs.getString("ThuongHieu")));
                sp.setDanhMuc(new DanhMuc(rs.getString("DanhMuc")));
                sp.setPhanLoai(new PhanLoai(rs.getString("PhanLoai")));
                sp.setChatLieu(new ChatLieu(rs.getString("ChatLieu")));
                list.add(sp);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SanPham> selectAll() {
        String selectAll = """
                         SELECT
                                sp.ID,
                                sp.ma_san_pham,
                                sp.ten,
                                sp.ngay_tao,
                                sp.ngay_sua,
                                th.ten_thuong_hieu as ThuongHieu,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai,
                                cl.chat_lieu as ChatLieu
                            FROM
                                dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID;
                       """;
        return this.selectBySql(selectAll);
    }

    public SanPham selectByMa(String ma) {
        String sql = """
              SELECT
                        sp.ID,
                        sp.ma_san_pham,
                        sp.ten,
                        sp.ngay_tao,
                        sp.ngay_sua,
                        th.ten_thuong_hieu as ThuongHieu,
                        dm.ten_danh_muc as DanhMuc,
                        pl.phan_loai as PhanLoai,
                        cl.chat_lieu as ChatLieu
                  FROM
                        dbo.SanPham sp
                    INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                    INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                    INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                    INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
               WHERE  sp.ma_san_pham LIKE ?
                   """;
        List<SanPham> list = this.selectBySql(sql, "%" + ma + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public List<SanPham> selectByKeyWord(String keyword) {
        String sql = """
                            SELECT
                                sp.ID,
                                sp.ma_san_pham,
                                sp.ten,
                                sp.ngay_tao,
                                sp.ngay_sua,
                                th.ten_thuong_hieu as ThuongHieu,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai,
                                cl.chat_lieu as ChatLieu
                            FROM
                                dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                     WHERE sp.ten LIKE ? OR sp.ma_san_pham LIKE ?
                     """;
        return this.selectBySql(sql,
                "%" + keyword + "%%",
                "%" + keyword + "%%");
    }

    public List<SanPham> searchKeyWord(String keyWord, int pages, int limit) {
        String sql = """
                     SELECT * 
                     FROM 
                     (
                         SELECT
                                sp.ID,
                                sp.ma_san_pham,
                                sp.ten,
                                sp.ngay_tao,
                                sp.ngay_sua,
                                th.ten_thuong_hieu as ThuongHieu,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai,
                                cl.chat_lieu as ChatLieu
                            FROM
                                dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                     WHERE sp.ten LIKE ? OR sp.ma_san_pham LIKE ?
                     ) AS FilteredResults
                     ORDER BY ID
                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        return this.selectBySql(sql,
                "%" + keyWord + "%%",
                "%" + keyWord + "%%",
                (pages - 1) * limit, limit);
    }

    public List<SanPham> selectPagesFilter(String danhMuc, String thuongHieu, String chatLieu, String keyword) {
        String sql = """
                            SELECT
                                sp.ID,
                                sp.ma_san_pham,
                                sp.ten,
                                sp.ngay_tao,
                                sp.ngay_sua,
                                th.ten_thuong_hieu as ThuongHieu,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai,
                                cl.chat_lieu as ChatLieu
                            FROM
                                dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                     WHERE dm.ten_danh_muc LIKE ? 
                            AND th.ten_thuong_hieu LIKE ?
                            AND cl.chat_lieu like ?
                            AND (sp.ten LIKE ? OR sp.ma_san_pham LIKE ?)
                     """;
        return this.selectBySql(sql,
                "%" + danhMuc + "%",
                "%" + thuongHieu + "%",
                "%" + chatLieu + "%",
                "%" + keyword + "%%",
                "%" + keyword + "%%");
    }

    public List<SanPham> Filter(String danhMuc, String thuongHieu, String chatLieu, String keyword, int pages, int limit) {
        String sql = """
                     SELECT * 
                     FROM 
                     (
                         SELECT
                                sp.ID,
                                sp.ma_san_pham,
                                sp.ten,
                                sp.ngay_tao,
                                sp.ngay_sua,
                                th.ten_thuong_hieu as ThuongHieu,
                                dm.ten_danh_muc as DanhMuc,
                                pl.phan_loai as PhanLoai,
                                cl.chat_lieu as ChatLieu
                            FROM
                                dbo.SanPham sp
                            INNER JOIN dbo.ChatLieu cl ON sp.chat_lieu_id = cl.ID
                            INNER JOIN dbo.DanhMuc dm ON sp.danh_muc_id = dm.ID
                            INNER JOIN dbo.PhanLoai pl ON sp.phan_loai_id = pl.ID
                            INNER JOIN dbo.ThuongHieu th ON sp.thuong_hieu_id = th.ID
                     WHERE dm.ten_danh_muc LIKE ? 
                            AND th.ten_thuong_hieu LIKE ?
                            AND cl.chat_lieu like ?
                            AND (sp.ten LIKE ? OR sp.ma_san_pham LIKE ?)
                     ) AS FilteredResults
                     ORDER BY ID
                     OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
                     """;
        return this.selectBySql(sql,
                "%" + danhMuc + "%",
                "%" + thuongHieu+ "%",
                "%" + chatLieu + "%",
                "%" + keyword + "%%",
                "%" + keyword + "%%",
                (pages - 1) * limit, limit);
    }
}
