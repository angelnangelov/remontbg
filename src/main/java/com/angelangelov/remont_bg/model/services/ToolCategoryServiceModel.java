package com.angelangelov.remont_bg.model.services;

import java.util.List;

public class ToolCategoryServiceModel {
    private String name;
    private String description;
    private List<ToolOfferServiceModel> tools;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ToolOfferServiceModel> getTools() {
        return tools;
    }

    public void setTools(List<ToolOfferServiceModel> tools) {
        this.tools = tools;
    }
}
