package model;

public class KhachHang {

    private Integer id;
    private String ma;
    private String ten;
    private String sdt;

    public KhachHang() {
    }

    public KhachHang(Integer id, String ma, String ten, String sdt) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.sdt = sdt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

}
