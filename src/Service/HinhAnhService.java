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
import model.HinhAnh;

public class HinhAnhService {

    public void insert(HinhAnh entity) {
        String sql = """
                        INSERT INTO [dbo].[Anh]
                                   ([link])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(HinhAnh entity) {
        String sql = """
                        UPDATE [dbo].[Anh]
                           SET [link] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[Anh]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public HinhAnh selectById(Integer id) {
        String selectById = """
                        select * from Anh where ID = ?
                        """;
        List<HinhAnh> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public HinhAnh selectByTen(String ten) {
        String selectById = """
                        select * from Anh where link like ?
                        """;
        List<HinhAnh> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<HinhAnh> selectBySql(String sql, Object... args) {
        List<HinhAnh> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HinhAnh ha = new HinhAnh();
                ha.setId(rs.getInt("ID"));
                ha.setTen(rs.getString("link"));
                list.add(ha);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<HinhAnh> selectAll() {
        String selectAll = """
                       select * from Anh
                       """;
        return this.selectBySql(selectAll);
    }

    public List<HinhAnh> selectPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM Anh
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }
}
