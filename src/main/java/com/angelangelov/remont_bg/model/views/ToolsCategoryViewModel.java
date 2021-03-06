package com.angelangelov.remont_bg.model.views;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;

import java.util.List;

public class ToolsCategoryViewModel {
    private ToolsCategoryName name;
    private String description;

    private List<ToolOffer> tools;
    public ToolsCategoryViewModel() {
    }

    public ToolsCategoryName getName() {
        return name;
    }

    public void setName(ToolsCategoryName name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ToolOffer> getTools() {
        return tools;
    }

    public void setTools(List<ToolOffer> tools) {
        this.tools = tools;
    }
}
