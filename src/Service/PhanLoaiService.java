/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.PhanLoai;

public class PhanLoaiService {

    public void insert(PhanLoai entity) {
        String sql = """
                        INSERT INTO [dbo].[PhanLoai]
                                   ([phan_loai])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(PhanLoai entity) {
        String sql = """
                        UPDATE [dbo].[PhanLoai]
                           SET [phan_loai] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[PhanLoai]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public PhanLoai selectById(Integer id) {
        String selectById = """
                        select * from PhanLoai where ID = ?
                        """;
        List<PhanLoai> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public PhanLoai selectByTen(String ten) {
        String selectById = """
                        select * from PhanLoai where phan_loai like ?
                        """;
        List<PhanLoai> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<PhanLoai> selectBySql(String sql, Object... args) {
        List<PhanLoai> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                PhanLoai pl = new PhanLoai();
                pl.setId(rs.getInt("ID"));
                pl.setTen(rs.getString("phan_loai"));
                list.add(pl);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PhanLoai> selectAll() {
        String selectAll = """
                       select * from PhanLoai
                       """;
        return this.selectBySql(selectAll);
    }

    public List<PhanLoai> searchPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM PhanLoai
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }
}
