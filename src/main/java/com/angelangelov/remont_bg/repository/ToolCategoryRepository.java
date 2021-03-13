package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolCategoryRepository extends JpaRepository<ToolCategory,String> {
    ToolCategory findByName(ToolsCategoryName name);
}
