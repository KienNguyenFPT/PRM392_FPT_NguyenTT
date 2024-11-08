package com.example.se1711qe170207nguyencaotrungkien.model;

import com.example.se1711qe170207nguyencaotrungkien.repo.BaseModel;

import java.util.HashMap;
import java.util.Map;

public class Tacgia extends BaseModel {
    private String TenTacgia;
    private String Email;
    private String Diachi;
    private String dienthoai;

    public Tacgia() {
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("TenTacgia", TenTacgia);
        map.put("Email", Email);
        map.put("Diachi", Diachi);
        map.put("Dienthoai", dienthoai);
        return map;
    }

    public Tacgia(String tenTacgia, String email, String diachi, String dienthoai) {
        TenTacgia = tenTacgia;
        Email = email;
        Diachi = diachi;
        this.dienthoai = dienthoai;
    }

    public String getTenTacgia() {
        return TenTacgia;
    }

    public void setTenTacgia(String tenTacgia) {
        TenTacgia = tenTacgia;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(String dienthoai) {
        this.dienthoai = dienthoai;
    }
}
