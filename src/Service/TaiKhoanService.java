/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import repository.DBContext;
import java.util.ArrayList;
import java.util.List;
import model.TaiKhoan;
import java.sql.*;

/**
 *
 * @author lenovo
 */
public class TaiKhoanService {
    
    
    public TaiKhoan selectByMa(String ma) throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();
        String selectByMa = """
                        select * from Taikhoan
                        WHERE tai_khoan = ?
                        """;
        
        PreparedStatement preparedStatement = DBContext.getConnection().prepareStatement(selectByMa);
        
        preparedStatement.setString(1, ma);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            list.add(new TaiKhoan(
                    resultSet.getInt("Id"), 
                    resultSet.getString("tai_khoan"), 
                    resultSet.getString("mat_khau"), 
                    resultSet.getString("ten"), 
                    resultSet.getString("dia_chi"), 
                    resultSet.getString("SDT"), 
                    resultSet.getString("email"), 
                    resultSet.getString("vai_tro")
            ));
        }
        
        
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
    public List<TaiKhoan> getAll() throws SQLException {
        List<TaiKhoan> list = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM Taikhoan";

        try (PreparedStatement preparedStatement = DBContext.getConnection().prepareStatement(selectAllQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                TaiKhoan taiKhoan = new TaiKhoan(
                        resultSet.getInt("Id"),
                        resultSet.getString("tai_khoan"),
                        resultSet.getString("mat_khau"),
                        resultSet.getString("ten"),
                        resultSet.getString("dia_chi"),
                        resultSet.getString("SDT"),
                        resultSet.getString("email"),
                        resultSet.getString("vai_tro")
                );
                list.add(taiKhoan);
            }
        }

        return list;
    }
}
