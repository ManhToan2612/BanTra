package com.toan04.bantra.model;

public class GioHang {
    private int maGioHang;
    private int maSanPham;
    private String maAD;
    private int soLuongMua;
    private String tenGiay;
    private int giaTien;
    private int soLuong;
    private boolean isSelected;
    private String anhSP;

    public GioHang() {
    }

    public GioHang(int maGioHang, int maSanPham, String maAD, int soLuongMua, String tenGiay, int giaTien, int soLuong, boolean isSelected, String anhSP) {
        this.maGioHang = maGioHang;
        this.maSanPham = maSanPham;
        this.maAD = maAD;
        this.soLuongMua = soLuongMua;
        this.tenGiay = tenGiay;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.isSelected = isSelected;
        this.anhSP = anhSP;
    }

    public int getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(int maGioHang) {
        this.maGioHang = maGioHang;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getMaAD() {
        return maAD;
    }

    public void setMaAD(String maAD) {
        this.maAD = maAD;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public String getTenGiay() {
        return tenGiay;
    }

    public void setTenGiay(String tenGiay) {
        this.tenGiay = tenGiay;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }
}
