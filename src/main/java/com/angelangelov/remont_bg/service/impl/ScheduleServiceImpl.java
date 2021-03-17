package com.angelangelov.remont_bg.service.impl;

import com.angelangelov.remont_bg.model.entities.Offer;
import com.angelangelov.remont_bg.model.entities.ToolOffer;
import com.angelangelov.remont_bg.repository.OfferRepository;
import com.angelangelov.remont_bg.repository.ToolOfferRepository;
import com.angelangelov.remont_bg.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ToolOfferRepository toolOfferRepository;
    private final OfferRepository offerRepository;

    @Autowired
    public ScheduleServiceImpl(ToolOfferRepository toolOfferRepository, OfferRepository offerRepository) {
        this.toolOfferRepository = toolOfferRepository;
        this.offerRepository = offerRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void checkOffersAndToolsDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println("Start work test");

        List<ToolOffer> tools = toolOfferRepository.findAll().stream().peek(tool ->
        {
            if (tool.getEndsOn().isBefore(localDate)) {
                tool.setActive(false);
            }
        }).collect(Collectors.toList());
        toolOfferRepository.saveAll(tools);

        List<Offer> offers = offerRepository.findAll().stream().peek(offer -> {
            if (offer.getEndsOn().isBefore(localDate)) {
                offer.setActive(false);
            }
        }).collect(Collectors.toList());
        offerRepository.saveAll(offers);
        System.out.println("End work test");

    }
}
