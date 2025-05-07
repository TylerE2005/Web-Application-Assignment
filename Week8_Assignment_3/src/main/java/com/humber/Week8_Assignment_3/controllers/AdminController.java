package com.humber.Week8_Assignment_3.controllers;

import com.humber.Week8_Assignment_3.models.Item;
import com.humber.Week8_Assignment_3.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/store/admin") //Created new folder for paths/methods locked behind Admin Role
public class AdminController {

    @Value("${store.name}") //Grabs value for store.name from application properties
    private String storeName;

    @Value("${page.size}") //Grabs value for page.size from application properties
    private int pageSize;

    @Autowired //Brings the ItemService bean for use
    ItemService itemService;

    //Opens Restock page
    @GetMapping("/restock")
    public String restock(Model model)
    {
        model.addAttribute("item", new Item());
        return "admin/restock";
    }

    //Will determine if you can add items into the database or not
    @PostMapping("/restock")
    public String saveRestock(Model model, @ModelAttribute Item item) {
        //Will try to save the item and gives the status code
        int statuscode = itemService.saveItem(item);

        //Determines if item can be added
        if (statuscode == 0) {
            //If 0, means it cannot be added and will return to restock page with error message
            model.addAttribute("message", "Item not saved, One of the Conditions is Wrong ");
            return "admin/restock";
        }
        //redirects to warehouse page with success message
        return "redirect:/store/warehouse/1?message=Item add Successfully";
    }

    //Delete method: will try to delete an item from the database by using its ID to check if present and then deleting using the ID
    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable int id) {
        int deleteStatusCode = itemService.deleteItemByID(id);
        //Checks if there's item to delete
        if (deleteStatusCode == 1) {
            return "redirect:/store/warehouse/1?message=Item Deleted Successfully";
        }
        return "redirect:/store/warehouse/1?message=Item Not Deleted, Doesn't Exist";
    }

    //Update method: will bring you to a modified restock page, made for updating, as well as brining the information for the object
    //you wish to update by its id
    @GetMapping("/update/{id}")
    public String updateItem(Model model, @PathVariable int id) {
        Optional<Item> itemToUpdate = itemService.getItemById(id);
        //Check if there's an item to update
        if (itemToUpdate.isPresent()) {
            model.addAttribute("item", itemToUpdate.get());
            return "admin/restock";
        }

        return "redirect:/store/warehouse/1?message=Item Not Updated, Doesn't Exist";
    }

    //Will update the given item in the database and return to warehouse page
    @PostMapping("/update")
    public String updateItem(@ModelAttribute Item item) {
        itemService.updateItem(item);
        return "redirect:/store/warehouse/1?message=Item Updated Successfully";
    }
}
