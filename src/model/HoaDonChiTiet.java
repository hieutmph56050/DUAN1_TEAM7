package model;

public class HoaDonChiTiet {

    private Integer id;
    private Integer id_HoaDon;
    private Integer id_SPCT;
    private Integer soLuong;
    private Double gia;
    private HoaDon hd;
    private SanPhamCT spct;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(Integer id, Integer id_HoaDon, Integer id_SPCT, Integer soLuong, Double gia, HoaDon hd, SanPhamCT spct) {
        this.id = id;
        this.id_HoaDon = id_HoaDon;
        this.id_SPCT = id_SPCT;
        this.soLuong = soLuong;
        this.gia = gia;
        this.hd = hd;
        this.spct = spct;
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

    public HoaDon getHd() {
        return hd;
    }

    public void setHd(HoaDon hd) {
        this.hd = hd;
    }

    public SanPhamCT getSpct() {
        return spct;
    }

    public void setSpct(SanPhamCT spct) {
        this.spct = spct;
    }

    public Double getTongTien() {
        return this.gia * this.soLuong;
    }
}
