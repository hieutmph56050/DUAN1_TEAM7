/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hieua
 */
public class HoaDon {
<<<<<<< HEAD

    private Integer id;
    private String ma;
    private Date ngayTao;
    private Double tongGia;
    private Integer trangThai;
    private Integer idNV;
    private Integer idKH;
    private Integer idKM;
    private NhanVien nv;
    private KhachHang kh;

    public HoaDon() {
    }

    public HoaDon(Integer id, String ma, Date ngayTao, Double tongGia, Integer trangThai, Integer idNV, Integer idKH) {
        this.id = id;
        this.ma = ma;
        this.ngayTao = ngayTao;
        this.tongGia = tongGia;
        this.trangThai = trangThai;
        this.idNV = idNV;
        this.idKH = idKH;
    }

    public HoaDon(String ma) {
        this.ma = ma;
    }
    
    public HoaDon(NhanVien nv) {
        this.nv = nv;
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

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Double getTongGia() {
        return tongGia;
    }

    public void setTongGia(Double tongGia) {
        this.tongGia = tongGia;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getIdNV() {
        return idNV;
    }

    public void setIdNV(Integer idNV) {
        this.idNV = idNV;
    }

    public Integer getIdKH() {
        return idKH;
    }

    public void setIdKH(Integer idKH) {
        this.idKH = idKH;
    }

    public NhanVien getNv() {
        return nv;
    }

    public void setNv(NhanVien nv) {
        this.nv = nv;
    }

    public KhachHang getKh() {
        return kh;
    }

    public void setKh(KhachHang kh) {
        this.kh = kh;
    }

=======
    
>>>>>>> 971136aa0af6cec4591770ccaf358d6fe863df93
}
