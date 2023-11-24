package model;

public class HoaDonChiTiet {

    private Integer id;
    private Integer id_HoaDon;
    private Integer id_SPCT;
    private Integer soLuong;
    private Double gia;
    private Integer id_KM;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(Integer id, Integer id_HoaDon, Integer id_SPCT, Integer soLuong, Double gia, Integer id_KM) {
        this.id = id;
        this.id_HoaDon = id_HoaDon;
        this.id_SPCT = id_SPCT;
        this.soLuong = soLuong;
        this.gia = gia;
        this.id_KM = id_KM;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_HoaDon() {
        return id_HoaDon;
    }

    public void setId_HoaDon(Integer id_HoaDon) {
        this.id_HoaDon = id_HoaDon;
    }

    public Integer getId_SPCT() {
        return id_SPCT;
    }

    public void setId_SPCT(Integer id_SPCT) {
        this.id_SPCT = id_SPCT;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getGia() {
        return gia;
    }

    public void setGia(Double gia) {
        this.gia = gia;
    }

    public Integer getId_KM() {
        return id_KM;
    }

    public void setId_KM(Integer id_KM) {
        this.id_KM = id_KM;
    }

}
