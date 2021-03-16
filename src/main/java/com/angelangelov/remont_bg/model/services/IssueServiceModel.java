package com.angelangelov.remont_bg.model.services;

import com.angelangelov.remont_bg.model.entities.User;

public class IssueServiceModel extends BaseServiceModel{
    private String problemName;
    private String problemDescription;
    private String problemImgUrl;
    private UserServiceModel user;

    public IssueServiceModel() {
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getProblemImgUrl() {
        return problemImgUrl;
    }

    public void setProblemImgUrl(String problemImgUrl) {
        this.problemImgUrl = problemImgUrl;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }
}
