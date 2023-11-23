/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class SanPham {

    private Integer id;
    private String ma;
    private String ten;
    private Date ngayThem;
    private Date ngaySua;
    private Integer id_cl;
    private Integer id_th;
    private Integer id_dm;
    private Integer id_pl;
    private ThuongHieu thuongHieu;
    private DanhMuc danhMuc;
    private ChatLieu chatLieu;
    private PhanLoai phanLoai;

    public SanPham() {
    }

    public SanPham(Integer id, String ma, String ten, Date ngayThem, Date ngaySua, Integer id_cl, Integer id_th, Integer id_dm, Integer id_pl, ThuongHieu thuongHieu, DanhMuc danhMuc, ChatLieu chatLieu, PhanLoai phanLoai) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.ngayThem = ngayThem;
        this.ngaySua = ngaySua;
        this.id_cl = id_cl;
        this.id_th = id_th;
        this.id_dm = id_dm;
        this.id_pl = id_pl;
        this.thuongHieu = thuongHieu;
        this.danhMuc = danhMuc;
        this.chatLieu = chatLieu;
        this.phanLoai = phanLoai;
    }

    public SanPham(String ma, String ten) {
        this.ma = ma;
        this.ten = ten;
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

    public Date getNgayThem() {
        return ngayThem;
    }

    public void setNgayThem(Date ngayThem) {
        this.ngayThem = ngayThem;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public Integer getId_cl() {
        return id_cl;
    }

    public void setId_cl(Integer id_cl) {
        this.id_cl = id_cl;
    }

    public Integer getId_th() {
        return id_th;
    }

    public void setId_th(Integer id_th) {
        this.id_th = id_th;
    }

    public Integer getId_dm() {
        return id_dm;
    }

    public void setId_dm(Integer id_dm) {
        this.id_dm = id_dm;
    }

    public Integer getId_pl() {
        return id_pl;
    }

    public void setId_pl(Integer id_pl) {
        this.id_pl = id_pl;
    }

    public ThuongHieu getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(ThuongHieu thuongHieu) {
        this.thuongHieu = thuongHieu;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public ChatLieu getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(ChatLieu chatLieu) {
        this.chatLieu = chatLieu;
    }

    public PhanLoai getPhanLoai() {
        return phanLoai;
    }

    public void setPhanLoai(PhanLoai phanLoai) {
        this.phanLoai = phanLoai;
    }

}
