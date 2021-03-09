package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.OfferCategory;
import com.angelangelov.remont_bg.model.entities.enums.ServiceOfferNames;
import com.angelangelov.remont_bg.model.services.OfferCategoryServiceModel;
import com.angelangelov.remont_bg.model.views.OfferCategoryViewModel;
import com.angelangelov.remont_bg.repository.OfferCategoryRepository;
import com.angelangelov.remont_bg.service.OfferCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferCategoryServiceImpl implements OfferCategoryService {
    private final OfferCategoryRepository offerCategoryRepository;
    private final ModelMapper modelMapper;

    public OfferCategoryServiceImpl(OfferCategoryRepository serviceCategoryRepository, ModelMapper modelMapper) {
        this.offerCategoryRepository = serviceCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initCategories() {
        ServiceOfferNames[] values = ServiceOfferNames.values();
        if(offerCategoryRepository.count()==0){
            Arrays.stream(ServiceOfferNames.values()).forEach(s->{
                OfferCategory serviceCategory = new OfferCategory(s, "Описание за категория " + s.name());
                offerCategoryRepository.save(serviceCategory);

            });
        }

    }

    @Override
    public List<OfferCategoryViewModel> getAllCategories() {
        return this.offerCategoryRepository.findAll().stream().map(
                a ->{
                    OfferCategoryViewModel offerCategoryServiceModel = this.modelMapper.map(a, OfferCategoryViewModel.class);
                    offerCategoryServiceModel.setOffers(a.getOffers());
                    return offerCategoryServiceModel;
                }
        ).collect(Collectors.toList());
    }




}
