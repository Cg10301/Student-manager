package com.example.demo;

import java.io.Serializable;

public class MonHoc implements Serializable {
    private String maMonHoc;
    private String tenMonHoc;
    private Integer soTinChi;

    public MonHoc(){}

    public MonHoc(String maMonHoc, String tenMonHoc, Integer soTinChi){
        this.maMonHoc = maMonHoc;
        this.tenMonHoc = tenMonHoc;
        this.soTinChi = soTinChi;
    }

    public String getMaMonHoc() {
        return maMonHoc;
    }

    public void setMaMonHoc(String maMonHoc) {
        this.maMonHoc = maMonHoc;
    }

    public String getTenMonHoc() {
        return tenMonHoc;
    }

    public void setTenMonHoc(String tenMonHoc) {
        this.tenMonHoc = tenMonHoc;
    }

    public Integer getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(Integer soTinChi) {
        this.soTinChi = soTinChi;
    }
}
