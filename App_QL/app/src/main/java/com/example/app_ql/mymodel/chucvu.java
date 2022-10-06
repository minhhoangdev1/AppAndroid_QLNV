package com.example.app_ql.mymodel;

public class chucvu {
    private  int id_cv;
    private  String ten_cv;
    private  String luong_cv;

    public chucvu(String ten_cv) {
        this.ten_cv = ten_cv;
    }

    public chucvu(int id_cv, String ten_cv) {
        this.id_cv = id_cv;
        this.ten_cv = ten_cv;
    }

    public chucvu(String ten_cv, String luong_cv) {
        this.ten_cv = ten_cv;
        this.luong_cv = luong_cv;
    }

    public int getId_cv() {
        return id_cv;
    }


    public String getTen_cv() {
        return ten_cv;
    }


    public String getLuong_cv() {
        return luong_cv;
    }

}
