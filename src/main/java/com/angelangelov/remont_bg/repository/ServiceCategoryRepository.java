package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory,String> {
}
