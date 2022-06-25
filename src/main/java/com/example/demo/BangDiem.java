package com.example.demo;

import java.io.Serializable;

public class BangDiem implements Serializable {
    private String maSV;
    private String tenSV;
    private String maMH;
    private Double diemQT = 0d;
    private Double diemHK = 0d;
    private Double diemTK = 0d;

    public BangDiem(){}

    public BangDiem(String maSV, String tenSV, String maMH){
        this.maSV = maSV;
        this.maMH = maMH;
        this.tenSV = tenSV;
    }

    public String getTenSV() {
        return tenSV;
    }

    public void setTenSV(String tenSV) {
        this.tenSV = tenSV;
    }



    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public Double getDiemQT() {
        return diemQT;
    }

    public void setDiemQT(Double diemQT) {
        this.diemQT = diemQT;
    }

    public Double getDiemHK() {
        return diemHK;
    }

    public void setDiemHK(Double diemHK) {
        this.diemHK = diemHK;
    }

    public Double getDiemTK() {
        return diemTK;
    }

    public void setDiemTK(Double diemTK) {
        this.diemTK = diemTK;
    }
}
