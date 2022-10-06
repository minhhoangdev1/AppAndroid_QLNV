package com.example.app_ql.mymodel;

public class nhanvien {
    private int id_nv;
    private String ten_nv;
    private String gioitinh;
    private String dienthoai;
    private String cmnd_cccd;
    private String ngaysinh	;
    private String diachi	;
    private  String ngaytuyendung;
    private String  loai_nv;
    private  int id_cv;

    public nhanvien() {
    }

    public void setTen_nv(String ten_nv) {
        this.ten_nv = ten_nv;
    }

    public void setId_nv(int id_nv) {
        this.id_nv = id_nv;
    }

    public nhanvien(String ten_nv, String gioitinh, String dienthoai, String cmnd_cccd, String ngaysinh, String diachi, String loai_nv) {
        this.ten_nv = ten_nv;
        this.gioitinh = gioitinh;
        this.dienthoai = dienthoai;
        this.cmnd_cccd = cmnd_cccd;
        this.ngaysinh = ngaysinh;
        this.diachi = diachi;
        this.loai_nv = loai_nv;
        this.id_cv = id_cv;
    }

    public nhanvien(int id_nv, String ten_nv, String gioitinh) {
        this.id_nv = id_nv;
        this.ten_nv = ten_nv;
        this.gioitinh = gioitinh;
    }

    public String getLoai_nv() {
        return loai_nv;
    }

    public int getId_cv() {
        return id_cv;
    }

    public int getId_nv() {
        return id_nv;
    }

    public String getTen_nv() {
        return ten_nv;
    }

    public String getGioitinh() {
        return gioitinh;
    }


    public String getDienthoai() {
        return dienthoai;
    }

    public String getCmnd_cccd() {
        return cmnd_cccd;
    }


    public String getNgaysinh() {
        return ngaysinh;
    }


    public String getDiachi() {
        return diachi;
    }


    public String getNgaytuyendung() {
        return ngaytuyendung;
    }



}
