package com.example.se1711_qe170207_nguyencaotrungkien.repo;


import java.util.HashMap;
import java.util.Map;

public class BaseModel {
    private String id;

    public BaseModel() {}

    public BaseModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }
}

