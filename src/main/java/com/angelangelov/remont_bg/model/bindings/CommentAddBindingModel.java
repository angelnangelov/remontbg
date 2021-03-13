package com.angelangelov.remont_bg.model.bindings;

import com.angelangelov.remont_bg.model.services.BaseServiceModel;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class CommentAddBindingModel extends BaseBindingModel {
    private String description;
    private LocalDateTime postedTime = LocalDateTime.now();

    public CommentAddBindingModel() {
    }

    @Length(min = 4, message = "Коментарът трябва да бъде минимум 4 символа!")
    @NotBlank(message = "Не може да се добавя празен коментар!")
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
