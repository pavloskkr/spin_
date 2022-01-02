package com.example.spin_.model;

import javax.persistence.*;

@Entity
@Table(name = "estates", schema = "db_schema")
public class Estate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estate_id", nullable = false)
    private Long estate_id;

    @Column(name = "availability",nullable = false)
    private String availability;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "square_meters", nullable = false)
    private Integer square_meters;

    @Column(name = "price", nullable = false)
    private Integer price;

    private String types;

    public Estate() {
    }

    public Estate(String availability, String location, Integer square_meters, Integer price, String types) {
        this.availability = availability;
        this.location = location;
        this.square_meters = square_meters;
        this.price = price;
        this.types = types;
    }

    public Estate(Long estate_id, String availability, String location, Integer square_meters, Integer price, String types) {
        this.estate_id = estate_id;
        this.availability = availability;
        this.location = location;
        this.square_meters = square_meters;
        this.price = price;
        this.types = types;
    }

    public Long getEstate_id() {
        return estate_id;
    }

    public void setEstate_id(Long estate_id) {
        this.estate_id = estate_id;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSquare_meters() {
        return square_meters;
    }

    public void setSquare_meters(Integer square_meters) {
        this.square_meters = square_meters;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }
}
