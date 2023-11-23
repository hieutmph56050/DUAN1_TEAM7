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
import model.HinhDang;

public class HinhDangService {

    public void insert(HinhDang entity) {
        String sql = """
                        INSERT INTO [dbo].[HinhDang]
                                   ([kieu_dang])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(HinhDang entity) {
        String sql = """
                        UPDATE [dbo].[HinhDang]
                           SET [kieu_dang] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[HinhDang]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public HinhDang selectById(Integer id) {
        String selectById = """
                        select * from HinhDang where ID = ?
                        """;
        List<HinhDang> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public HinhDang selectByTen(String ten) {
        String selectById = """
                        select * from HinhDang where kieu_dang like ?
                        """;
        List<HinhDang> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<HinhDang> selectBySql(String sql, Object... args) {
        List<HinhDang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HinhDang hd = new HinhDang();
                hd.setId(rs.getInt("ID"));
                hd.setTen(rs.getString("kieu_dang"));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<HinhDang> selectAll() {
        String selectAll = """
                       select * from HinhDang
                       """;
        return this.selectBySql(selectAll);
    }

    public List<HinhDang> searchPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM HinhDang
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }
}
