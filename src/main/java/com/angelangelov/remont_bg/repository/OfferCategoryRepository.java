package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferCategoryRepository extends JpaRepository<OfferCategory,String> {
    OfferCategoryServiceModel findByName(ServiceOfferNames name);
}
