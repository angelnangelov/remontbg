package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.entities.ToolCategory;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.entities.enums.ToolsCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolOfferRepository extends JpaRepository<ToolOffer,String> {

}
