/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.util.ArrayList;
import java.util.List;
import model.MauSac;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ledin
 */
public class MauSacService {

    public void insert(MauSac entity) {
        String sql = """
                        INSERT INTO [dbo].[MauSac]
                                   ([ten_mau])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(MauSac entity) {
        String sql = """
                        UPDATE [dbo].[MauSac]
                           SET [ten_mau] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[MauSac]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public MauSac selectById(Integer id) {
        String selectById = """
                        select * from MauSac where ID = ?
                        """;
        List<MauSac> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public MauSac selectByTen(String ten) {
        String selectById = """
                        select * from MauSac where ten_mau like ?
                        """;
        List<MauSac> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<MauSac> selectBySql(String sql, Object... args) {
        List<MauSac> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                MauSac ms = new MauSac();
                ms.setId(rs.getInt("ID"));
                ms.setTen(rs.getString("ten_mau"));
                list.add(ms);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MauSac> selectAll() {
        String selectAll = """
                       select * from MauSac
                       """;
        return this.selectBySql(selectAll);
    }

    public List<MauSac> selectPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM MauSac
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }
}
