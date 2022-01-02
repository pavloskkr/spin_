package com.example.spin_.controllers;

import com.example.spin_.model.Estate;
import com.example.spin_.model.JoinedEstateTypes;
import com.example.spin_.repositories.impl.EstateRepoImpl;
import com.example.spin_.repositories.impl.TypeRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/S2")
public class MainController<allAvailableTypes> {

    private final EstateRepoImpl estateRepoImpl;
    private final TypeRepoImpl typeRepoImpl;
    private List<JoinedEstateTypes> allAvailableTypes;
    private Map<Long,String> typesMap = new HashMap<>();


    @Autowired
    public MainController(EstateRepoImpl estateRepoImpl, TypeRepoImpl typeRepoImpl) {
        this.estateRepoImpl = estateRepoImpl;
        this.typeRepoImpl = typeRepoImpl;
    }

    @GetMapping
    public List<Estate> getEstate() {
        List<JoinedEstateTypes> finalMappedTypesList = new ArrayList<>();
        List<Estate> allAvailabeEstates = new ArrayList<>();

        allAvailableTypes = typeRepoImpl.getAllJoinedEstates();
        typesMap = allAvailableTypes.stream().collect(Collectors.toMap(JoinedEstateTypes::geteId, JoinedEstateTypes::getTypeName, (typeX,typeY) -> typeX + "," + typeY));
        allAvailabeEstates = estateRepoImpl.getEstate();

        for (Map.Entry<Long, String> entry : typesMap.entrySet()) {
            JoinedEstateTypes joinedEstateTypes = new JoinedEstateTypes(entry.getKey(),entry.getValue());
            finalMappedTypesList.add(joinedEstateTypes);
        }

        for (int i=0; i< finalMappedTypesList.size(); i++) {
            for (int j=0; j< allAvailabeEstates.size(); j++) {
                allAvailabeEstates.get(i).setTypes(finalMappedTypesList.get(i).getTypeName());
            }
        }
        return allAvailabeEstates;
    }

    public List<JoinedEstateTypes> getAllJoinedEstates() {
        return  typeRepoImpl.getAllJoinedEstates();
    }

    public List<String> getAllAvailableLocations() {
        return estateRepoImpl.getAllAvailableLocations();
    }

    public List<String> getAllAvailableAvailabilityTypes() {
        return estateRepoImpl.getAllAvailableAvailabilityTypes();
    }

    public List<String> getAllDistinctTypes() {
        return typeRepoImpl.getAllDistinctTypes();
    }

    @Controller
    public class HomeController {



        @GetMapping(path = "/index")
        public String getIndex(Model model) {

            model.addAttribute("locations", getAllAvailableLocations());
            model.addAttribute("availabilityTypes",getAllAvailableAvailabilityTypes());
            model.addAttribute("types", getAllDistinctTypes());
            model.addAttribute("estates", getEstate()); //to bring the concatenated types

            return "index";
        }

        @GetMapping(path = "/search")
        public String makeSearch(Model model,
                          @RequestParam(name = "priceFrom",required = false) Integer priceFrom,
                          @RequestParam(name = "priceTo",required = false) Integer priceTo,
                          @RequestParam(name = "squareFrom",required = false) Integer squareFrom,
                          @RequestParam(name = "squareTo",required = false) Integer squareTo,
                          @RequestParam(name = "location",required = false) List<String> location,
                          @RequestParam(name = "type",required = false) List<String> type,
                          @RequestParam(name="availability",required = false)String availability) {

            model.addAttribute("locations", getAllAvailableLocations());
            model.addAttribute("availabilityTypes",getAllAvailableAvailabilityTypes());
            model.addAttribute("types", getAllDistinctTypes());
            model.addAttribute("estates", getEstate()); //to bring the concatenated types

            if (priceFrom == null ) {
                priceFrom = 10;
            }
            if (priceTo == null) {
                priceTo = 10000000;
            }

            if (squareFrom == null ) {
                squareFrom = 20;
            }

            if (squareTo == null ) {
                squareTo = 10000;
            }

            if (type == null ) {
                type = typeRepoImpl.getAllDistinctTypes();
            }

            model.addAttribute("estates",estateRepoImpl.getEstatesByFilters(priceFrom,priceTo,squareFrom,squareTo,
                    location,type,availability));

            return "index";
        }

    }
}
