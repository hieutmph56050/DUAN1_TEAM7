package model;

public class ChatLieu {

    Integer id;
    String ten;

    public ChatLieu() {
    }

    public ChatLieu(Integer id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public ChatLieu(String ten) {
        this.ten = ten;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @Override
    public String toString() {
        return this.ten;
    }
}
