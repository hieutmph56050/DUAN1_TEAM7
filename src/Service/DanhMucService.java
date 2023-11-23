/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import model.DanhMuc;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ledin
 */
public class DanhMucService {

    public void insert(DanhMuc entity) {
        String sql = """
                        INSERT INTO [dbo].[DanhMuc]
                                   ([ten_danh_muc])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(DanhMuc entity) {
        String sql = """
                        UPDATE [dbo].[DanhMuc]
                           SET [ten_danh_muc] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[DanhMuc]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public DanhMuc selectById(Integer id) {
        String selectById = """
                        select * from DanhMuc where ID = ?
                        """;
        List<DanhMuc> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public DanhMuc selectByTen(String ten) {
        String selectById = """
                        select * from DanhMuc where ten_danh_muc like ?
                        """;
        List<DanhMuc> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<DanhMuc> selectBySql(String sql, Object... args) {
        List<DanhMuc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setId(rs.getInt("ID"));
                danhMuc.setTen(rs.getString("ten_danh_muc"));
                list.add(danhMuc);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DanhMuc> selectAll() {
        String selectAll = """
                       select * from DanhMuc
                       """;
        return this.selectBySql(selectAll);
    }

    public List<DanhMuc> selectPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM DanhMuc
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }
}
