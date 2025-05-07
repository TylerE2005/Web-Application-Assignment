package com.humber.Week8_Assignment_3.repositories;

import com.humber.Week8_Assignment_3.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//This Repository extends JpaRepository to allow the use of JPA built-in Crud operations
//Item is the object and integer is the data type of the primary key
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    //Custom Query to sort the Table by Brand
    @Query("SELECT i FROM Item i WHERE i.brand = :brand AND i.creationDate = 2022")
    List<Item> findByBrandAndYear(@Param("brand") String brand);

    List<Item> findAllByOrderByBrandAsc();
}
