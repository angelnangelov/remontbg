package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.ServiceCategory;
import com.angelangelov.remont_bg.model.entities.ServiceOffer;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.repository.ServiceCategoryRepository;
import com.angelangelov.remont_bg.service.ServiceCategoryService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    private final ServiceCategoryRepository serviceCategoryRepository;

    public ServiceCategoryServiceImpl(ServiceCategoryRepository serviceCategoryRepository) {
        this.serviceCategoryRepository = serviceCategoryRepository;
    }

    @Override
    public void initCategories() {
        ServiceOfferNames[] values = ServiceOfferNames.values();
        if(serviceCategoryRepository.count()==0){
            Arrays.stream(ServiceOfferNames.values()).forEach(s->{
                ServiceCategory serviceCategory = new ServiceCategory(s, "Описание за категория " + s.name());
                serviceCategoryRepository.save(serviceCategory);

            });
        }

    }
}
