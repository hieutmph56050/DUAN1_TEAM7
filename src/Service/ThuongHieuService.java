/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import model.ThuongHieu;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ledin
 */
public class ThuongHieuService {

    public void insert(ThuongHieu entity) {
        String sql = """
                        INSERT INTO [dbo].[ThuongHieu]
                                   ([ten_thuong_hieu])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(ThuongHieu entity) {
        String sql = """
                        UPDATE [dbo].[ThuongHieu]
                           SET [ten_thuong_hieu] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[ThuongHieu]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public ThuongHieu selectById(Integer id) {
        String selectById = """
                        select * from ThuongHieu where ID = ?
                        """;
        List<ThuongHieu> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public ThuongHieu selectByTen(String ten) {
        String selectById = """
                        select * from ThuongHieu where ten_thuong_hieu like ?
                        """;
        List<ThuongHieu> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<ThuongHieu> selectBySql(String sql, Object... args) {
        List<ThuongHieu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ThuongHieu th = new ThuongHieu();
                th.setId(rs.getInt("ID"));
                th.setTen(rs.getString("ten_thuong_hieu"));
                list.add(th);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ThuongHieu> selectAll() {
        String selectAll = """
                       select * from ThuongHieu
                       """;
        return this.selectBySql(selectAll);
    }

    public List<ThuongHieu> searchPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM ThuongHieu
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }

}
