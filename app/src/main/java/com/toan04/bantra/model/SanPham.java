package com.toan04.bantra.model;

public class SanPham {
    private int maSanPham;
    private String tenSanPham;
    private int maLoai;
    private int giaTien;
    private String tenLoai;
    private int soLuong;
    private String anh;

    public SanPham() {
    }

    public SanPham(int maSanPham, String tenSanPham, int maLoai, int giaTien, String tenLoai, int soLuong, String anh) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.maLoai = maLoai;
        this.giaTien = giaTien;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
        this.anh = anh;
    }
    public SanPham(int maGiay, String tenGiay, int giaTien, int maLoai, int soLuong, String anh) {
        this.maSanPham = maGiay;
        this.tenSanPham = tenGiay;
        this.giaTien = giaTien;
        this.maLoai = maLoai;
        this.soLuong = soLuong;
        this.anh = anh;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
