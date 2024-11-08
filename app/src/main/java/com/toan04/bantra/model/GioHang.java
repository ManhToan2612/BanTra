package com.toan04.bantra.model;

public class GioHang {
    private int maGioHang;
    private int maSanPham;
    private String maAD;
    private int soLuongMua;
    private String tenTra;
    private int giaTien;
    private int soLuong;
    private boolean isSelected;
    private String anhSP;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public GioHang() {
    }

    public GioHang(int maGiay, String maAD, int soLuongMua) {
        this.maSanPham = maGiay;
        this.maAD = maAD;
        this.soLuongMua = soLuongMua;
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

    public String getTenTra() {
        return tenTra;
    }

    public void setTenTra(String tenTra) {
        this.tenTra = tenTra;
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


    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }
}
