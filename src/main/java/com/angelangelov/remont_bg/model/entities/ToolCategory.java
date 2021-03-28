package com.angelangelov.remont_bg.model.entities;

import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tools_categories")
public class ToolCategory extends BaseEntity {
    private ToolsCategoryName name;
    private String description;
    private List<ToolOffer> tools;

    public ToolCategory() {
    }
    public ToolCategory(ToolsCategoryName name, String description) {
        this.name = name;
        this.description=description;
    }





    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    public ToolsCategoryName getName() {
        return name;
    }

    public void setName(ToolsCategoryName name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false,  columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @OneToMany(mappedBy = "toolCategory",fetch = FetchType.EAGER)
    public List<ToolOffer> getTools() {
        return tools;
    }

    public void setTools(List<ToolOffer> tools) {
        this.tools = tools;
    }
}
