package com.example.spin_.repositories.impl;

import com.example.spin_.model.JoinedEstateTypes;
import com.example.spin_.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeRepoImpl {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeRepoImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<String> getAllDistinctTypes() {
        return typeRepository.findAllDistinctTypeNames(Sort.by("type_name"));
    }

    public List<JoinedEstateTypes> getAllJoinedEstates() {
        return typeRepository.findAllJoinedEstates();
    }

}
