package com.angelangelov.remont_bg.service;

import com.angelangelov.remont_bg.model.entities.LogEntity;

import java.util.List;

public interface LogService {
    void createLog(String action, String offerId);
    List<String> find3MostViewed();
}
