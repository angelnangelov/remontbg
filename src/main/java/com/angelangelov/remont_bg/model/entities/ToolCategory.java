package com.angelangelov.remont_bg.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tools_categories")
public class ToolCategory extends BaseCategory {

    private String imgUrl;
    private List<ToolOffer> tools;


    public ToolCategory() {
    }

    @Column(name = "img_url")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @OneToMany(mappedBy = "toolCategory")
    public List<ToolOffer> getTools() {
        return tools;
    }

    public void setTools(List<ToolOffer> tools) {
        this.tools = tools;
    }
}
