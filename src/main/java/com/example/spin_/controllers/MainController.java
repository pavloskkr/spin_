package com.example.spin_.controllers;

import com.example.spin_.model.Estate;
import com.example.spin_.model.JoinedEstateTypes;
import com.example.spin_.repositories.impl.EstateRepoImpl;
import com.example.spin_.repositories.impl.TypeRepoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/S2")
public class MainController<allAvailableTypes> {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    private final EstateRepoImpl estateRepoImpl;
    private final TypeRepoImpl typeRepoImpl;
    private List<JoinedEstateTypes> allAvailableTypes;
    private Map<Long,String> typesMap = new HashMap<>();


    @Autowired
    public MainController(EstateRepoImpl estateRepoImpl, TypeRepoImpl typeRepoImpl) {
        this.estateRepoImpl = estateRepoImpl;
        this.typeRepoImpl = typeRepoImpl;
    }

    public List<Estate> getEstate() {
        List<JoinedEstateTypes> finalMappedTypesList = new ArrayList<>();
        List<Estate> allAvailabeEstates = new ArrayList<>();

        /* Joined type names to object estate and storing it into map
           Converted the map into object list
           Filling the initial list with the updated type names */
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

    // Retrieving all available estates joined by type name
    public List<JoinedEstateTypes> getAllJoinedEstates() {
        return  typeRepoImpl.getAllJoinedEstates();
    }

    // Retrieving all available locations
    public List<String> getAllAvailableLocations() {
        return estateRepoImpl.getAllAvailableLocations();
    }

    // Retrieving the availability
    public List<String> getAllAvailableAvailabilityTypes() {
        return estateRepoImpl.getAllAvailableAvailabilityTypes();
    }

    // Retrieving all available distinct types of estate
    public List<String> getAllDistinctTypes() {
        return typeRepoImpl.getAllDistinctTypes();
    }

    @Controller
    public class HomeController {

        // Loading the home page with its elements filled in
        @GetMapping(path = "/index")
        public String getIndex(Model model) {

            model.addAttribute("locations", getAllAvailableLocations());
            model.addAttribute("availabilityTypes",getAllAvailableAvailabilityTypes());
            model.addAttribute("types", getAllDistinctTypes());
            model.addAttribute("estates", getEstate()); //retrieving the concatenated types

            return "index";
        }

        // Perform the search according to user's filters
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
            model.addAttribute("estates", getEstate()); //retrieving the concatenated types

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

            logger.info("===================== User selections ==============================");
            logger.info("User selected priceFrom : " + priceFrom);
            logger.info("User selected priceTo : " + priceTo);
            logger.info("User selected squareFrom : " + squareFrom);
            logger.info("User selected squareTo : " + squareTo);
            logger.info("User selected location : " + location);
            logger.info("User selected type : " + type);
            logger.info("User selected availability : " + availability);
            logger.info("=======================================================================");

            try {
                // Query according to user's selections.
                model.addAttribute("estates",estateRepoImpl.getEstatesByFilters(priceFrom,priceTo,squareFrom,squareTo,location,type,availability));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "index";
        }

    }
}
