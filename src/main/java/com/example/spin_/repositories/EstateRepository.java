package com.example.spin_.repositories;

import com.example.spin_.model.Estate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstateRepository extends JpaRepository<Estate, Long> {

    @Query("select distinct s.location from Estate s")
    List<String> findAllDistinctLocations(Sort sort);

    @Query("select distinct s.availability from Estate s")
    List<String> findAllDistinctAvailabilityTypes(Sort sort);

    @Query(value = "select * from {h-schema}estates e inner join {h-schema}type t on e.estate_id = t.estate_id where e.location in (:location)" +
                   " and e.price between :priceFrom and :priceTo" +
                   " and e.square_meters between :squareFrom and :squareTo and t.type_name in (:type)" +
                   " and e.availability = :availability", nativeQuery = true)
    List<Estate> findEstatesByFilters(@Param("priceFrom") Integer priceFrom,
                                     @Param("priceTo") Integer priceTo,
                                     @Param("squareFrom") Integer squareFrom,
                                     @Param("squareTo") Integer squareTo,
                                     @Param("location") List<String> location,
                                     @Param("type") List<String> type,
                                     @Param("availability") String availability);

}
