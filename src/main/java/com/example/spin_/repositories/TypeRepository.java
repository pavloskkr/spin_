package com.example.spin_.repositories;

import com.example.spin_.model.JoinedEstateTypes;
import com.example.spin_.model.Type;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query("select distinct t.type_name from Type t")
    List<String> findAllDistinctTypeNames(Sort sort);

    @Query("select NEW com.example.spin_.model.JoinedEstateTypes(t.estate.estate_id,t.type_name) from Type t order by t.estate.estate_id")
    List<JoinedEstateTypes> findAllJoinedEstates();
}
