package com.angelangelov.remont_bg.repository;

import com.angelangelov.remont_bg.model.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository  extends JpaRepository<Offer,String> {
    List<Offer> findAllByApprovedFalse();
}
