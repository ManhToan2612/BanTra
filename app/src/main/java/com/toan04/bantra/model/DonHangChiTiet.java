package com.toan04.bantra.model;

public class DonHangChiTiet {
    private int maChiTietDonHang;
    private int maDonHang;
    private int maSanPham;
    private String tenSanPham;
    private int soLuong;
    private int donGia;
    private int thanhTien;
    private  String anhsp;

    public DonHangChiTiet() {
    }

    public DonHangChiTiet(int maDonHang, int maGiay, int soLuong, int donGia, int thanhTien) {
        this.maDonHang = maDonHang;
        this.maSanPham = maGiay;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public DonHangChiTiet(int maChiTietDonHang, String anhsp, int thanhTien, int donGia, int soLuong, String tenSanPham, int maSanPham, int maDonHang) {
        this.maChiTietDonHang = maChiTietDonHang;
        this.anhsp = anhsp;
        this.thanhTien = thanhTien;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.tenSanPham = tenSanPham;
        this.maSanPham = maSanPham;
        this.maDonHang = maDonHang;
    }

    public int getMaChiTietDonHang() {
        return maChiTietDonHang;
    }

    public void setMaChiTietDonHang(int maChiTietDonHang) {
        this.maChiTietDonHang = maChiTietDonHang;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(int thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getAnhsp() {
        return anhsp;
    }

    public void setAnhsp(String anhsp) {
        this.anhsp = anhsp;
    }
}
