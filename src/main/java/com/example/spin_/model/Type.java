package com.example.spin_.model;

import javax.persistence.*;

@Entity
@Table(name = "type", schema = "db_schema")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false)
    private Long type_id;

    @Column(name = "type_name", nullable = false)
    private String type_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estate_id")
    private Estate estate;

    public Type() {
    }

    public Type(Long type_id, String type_name, Estate estate) {
        this.type_id = type_id;
        this.type_name = type_name;
        this.estate = estate;
    }

    public Type(String type_name, Estate estate) {
        this.type_name = type_name;
        this.estate = estate;
    }

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

}
