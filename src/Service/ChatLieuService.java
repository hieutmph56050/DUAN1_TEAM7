/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ChatLieu;

/**
 *
 * @author ledin
 */
public class ChatLieuService {

    public void insert(ChatLieu entity) {
        String sql = """
                        INSERT INTO [dbo].[ChatLieu]
                                   ([chat_lieu])
                             VALUES (?)
                        """;

        JdbcHelper.update(sql,
                entity.getTen());
    }

    public void update(ChatLieu entity) {
        String sql = """
                        UPDATE [dbo].[ChatLieu]
                           SET [chat_lieu] = ?
                         WHERE ID = ?
                        """;

        JdbcHelper.update(sql,
                entity.getTen(),
                entity.getId());
    }

    public void delete(Integer id) {
        String delete_sql = """
                        DELETE FROM [dbo].[ChatLieu]
                              WHERE ID = ?
                        """;

        JdbcHelper.update(delete_sql, id);
    }

    public ChatLieu selectById(Integer id) {
        String selectById = """
                        select * from ChatLieu where ID = ?
                        """;
        List<ChatLieu> list = this.selectBySql(selectById, id);
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public ChatLieu selectByTen(String ten) {
        String selectById = """
                        select * from ChatLieu where chat_lieu like ?
                        """;
        List<ChatLieu> list = this.selectBySql(selectById, "%" + ten + "%");
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    protected List<ChatLieu> selectBySql(String sql, Object... args) {
        List<ChatLieu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChatLieu chatLieu = new ChatLieu();
                chatLieu.setId(rs.getInt("ID"));
                chatLieu.setTen(rs.getString("chat_lieu"));
                list.add(chatLieu);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ChatLieu> selectAll() {
        String selectAll = """
                       select * from ChatLieu
                       """;
        return this.selectBySql(selectAll);
    }

    public List<ChatLieu> selectPages(int page, int limit) {
        String sql = """
                       SELECT * 
                       FROM 
                       (
                           SELECT * 
                           FROM ChatLieu
                       ) AS FilteredResults
                       ORDER BY ID
                       OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;
                       """;
        return this.selectBySql(sql, (page - 1) * limit, limit);
    }

}
