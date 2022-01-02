package com.example.spin_.repositories.impl;

import com.example.spin_.model.Estate;
import com.example.spin_.repositories.EstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstateRepoImpl {

    private final EstateRepository estateRepository;

    @Autowired
    public EstateRepoImpl(EstateRepository estateRepository) {
        this.estateRepository = estateRepository;
    }

    public List<Estate> getEstate() {
        try {
            return estateRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estateRepository.findAll();
    }

    public List<String> getAllAvailableLocations() {
        return estateRepository.findAllDistinctLocations(Sort.by("location"));
    }

    public List<String> getAllAvailableAvailabilityTypes() {
        return estateRepository.findAllDistinctAvailabilityTypes(Sort.by("availability"));
    }

    public List<Estate> getEstatesByFilters(Integer priceFrom,Integer priceTo,
                                            Integer squareFrom,Integer squareTo,
                                            List<String> location,List<String> type,
                                            String availability) {
        return estateRepository.findEstatesByFilters(priceFrom,priceTo,squareFrom,squareTo,
                                                    location,type,availability);
    }
}
