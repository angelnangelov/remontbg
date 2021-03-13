package com.angelangelov.remont_bg.model.bindings;

import com.angelangelov.remont_bg.model.services.BaseServiceModel;

import java.time.LocalDateTime;

public class CommentAddBindingModel extends BaseBindingModel {
    private String description;
    private LocalDateTime postedTime = LocalDateTime.now();

    public CommentAddBindingModel() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
    }
}
