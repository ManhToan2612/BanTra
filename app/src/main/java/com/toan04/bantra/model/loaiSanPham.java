package com.toan04.bantra.model;

public class loaiSanPham {
    private int maLoai;
    private String tenLoai;

    public loaiSanPham() {
    }

    public loaiSanPham(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
