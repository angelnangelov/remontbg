package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.ToolCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolCategoryRepository extends JpaRepository<ToolCategory,String> {
}
