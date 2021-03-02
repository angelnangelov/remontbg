package com.angelangelov.remont_bg.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {
    private String problemName;
    private String problemDescription;
    private String problemImgUrl;
    private User user;

    public Issue() {
    }

    @Column(name = "problem_name", nullable = false)
    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    @Column(name = "problem_description", nullable = false)
    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    @Column(name = "problem_img_url")
    public String getProblemImgUrl() {
        return problemImgUrl;
    }

    public void setProblemImgUrl(String problemImgUrl) {
        this.problemImgUrl = problemImgUrl;
    }


    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
