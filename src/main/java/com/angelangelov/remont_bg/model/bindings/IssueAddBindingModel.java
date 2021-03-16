package com.angelangelov.remont_bg.model.bindings;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

public class IssueAddBindingModel extends BaseBindingModel{
    private String problemName;
    private String problemDescription;
    private MultipartFile problemImgUrl;

    public IssueAddBindingModel() {
    }

    @NotBlank
    @Length(min = 3,max = 15,message = "Заглавието на проблема не може да бъде по-малко от 3 и по-голямо от 15 символа!")
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }
    @NotBlank(message = "Това поле е задължително!")
    @Length(min = 5, message = "Минимум 5 символа")
    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public MultipartFile getProblemImgUrl() {
        return problemImgUrl;
    }

    public void setProblemImgUrl(MultipartFile problemImgUrl) {
        this.problemImgUrl = problemImgUrl;
    }
}
