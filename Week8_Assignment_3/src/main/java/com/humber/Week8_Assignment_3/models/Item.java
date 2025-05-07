package com.humber.Week8_Assignment_3.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
public class Item {
    //Makes the 'ID' field as database primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String brand;
    private int creationDate;
    private double price;

    //Constructor to allow CommandLineReader to populate the Table without needing ID
    public Item(String name, String brand, int creationDate, double price) {
        this.name = name;
        this.brand = brand;
        this.creationDate = creationDate;
        this.price = price;
    }
}
