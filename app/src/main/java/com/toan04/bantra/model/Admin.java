package com.toan04.bantra.model;

public class Admin {
    private String maAD;
    private String hoTen;
    private String matKhau;
    private String loaiTK;
    private String anh;
    private String sdt;
    private String diaChi;

    public Admin() {
    }

    public Admin(String maAD, String hoTen, String matKhau, String loaiTK, String anh, String sdt, String diaChi) {
        this.maAD = maAD;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.loaiTK = loaiTK;
        this.anh = anh;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public String getMaAD() {
        return maAD;
    }

    public void setMaAD(String maAD) {
        this.maAD = maAD;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getLoaiTK() {
        return loaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        this.loaiTK = loaiTK;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
