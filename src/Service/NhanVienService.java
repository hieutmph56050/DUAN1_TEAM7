package Service;

import java.util.ArrayList;
import java.util.List;
import model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import repository.JdbcHelper;

public class NhanVienService {

    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(rs.getInt("ID"));
                nv.setTaiKhoan(rs.getString("tai_khoan"));
                nv.setTen(rs.getString("ten"));
                nv.setDiaChi(rs.getString("dia_chi"));
                nv.setSdt(rs.getString("SDT"));
                nv.setEmail(rs.getString("email"));
                nv.setMatKhau(rs.getString("mat_khau"));
                nv.setVaiTro(rs.getInt("vai_tro"));
                list.add(nv);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public NhanVien selectByMa(String ma) {
        String selectByMa = """
                        select * from Taikhoan
                        WHERE tai_khoan = ?
                        """;
        List<NhanVien> list = this.selectBySql(selectByMa, ma);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}
