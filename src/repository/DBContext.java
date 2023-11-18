/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.*;


public class DBContext {

    private static final String DATABASE = "PRO1041";
    private static final String USER = "sa";
    private static final String PASS = "songlong";

    public static Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DATABASE + ";encrypt=false;trustservercertificate=true";
        try {
            return DriverManager.getConnection(url, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = getConnection().prepareCall(sql);
        } else {
            stmt = getConnection().prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement stmt = DBContext.getStmt(sql, args);
        return stmt.executeQuery();
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = DBContext.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(String sql, Object... args) {
        try {
            PreparedStatement stmt = DBContext.getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
