package com.example.se1711_qe170207_nguyencaotrungkien.model;

import com.example.se1711_qe170207_nguyencaotrungkien.repo.BaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sach extends BaseModel {
    private String Tensach;
    private Date NgayXB;
    private String theloai;
    private String idTacgia;

    public Sach() {
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("Tensach", Tensach);
        map.put("NgayXB", NgayXB);
        map.put("Theloai", theloai);
        map.put("idTacgia", idTacgia);
        return map;
    }

    public Sach(String tensach, Date ngayXB, String theloai, String idTacgia) {
        Tensach = tensach;
        NgayXB = ngayXB;
        this.theloai = theloai;
        this.idTacgia = idTacgia;
    }

    public String getTensach() {
        return Tensach;
    }

    public void setTensach(String tensach) {
        Tensach = tensach;
    }

    public Date getNgayXB() {
        return NgayXB;
    }

    public void setNgayXB(Date ngayXB) {
        NgayXB = ngayXB;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getTacgia() {
        return idTacgia;
    }

    public void setTacgia(String tacgia) {
        this.idTacgia = tacgia;
    }
}