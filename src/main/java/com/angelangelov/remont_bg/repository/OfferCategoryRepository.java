package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OfferCategoryRepository extends JpaRepository<OfferCategory,String> {
    OfferCategory findByName(ServiceOfferNames name);


}
