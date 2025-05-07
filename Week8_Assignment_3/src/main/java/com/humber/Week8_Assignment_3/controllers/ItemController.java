package com.humber.Week8_Assignment_3.controllers;


import com.humber.Week8_Assignment_3.models.Item;
import com.humber.Week8_Assignment_3.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/store")
public class ItemController {
    @Autowired
    ItemService itemService;

    //Takes the store.name field from application.properties
    @Value("${store.name}")
    private String storeName;

    //Takes the page.size field from application.properties
    @Value("${page.size}")
    private int pageSize;

    //Home Method
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", storeName);
        return "home";
    }

    //Warehouse Method: will get paginated page and allows for sorting of the page
    @GetMapping("/warehouse/{pageNo}")
    public String warehouse(Model model, @RequestParam(required = false) String message
            , @PathVariable int pageNo,
                       @RequestParam(required = false, defaultValue = "id") String sortField,
                       @RequestParam(required = false, defaultValue = "asc") String sortDirection ) {
        Page<Item> page;

        if ("brand".equals(sortField)) {
            page = itemService.getPaginatedSortedItemsByBrand(pageNo, pageSize, sortDirection);
        } else {
            page = itemService.getPaginatedSortedItems(pageNo, pageSize, sortField, sortDirection);
        }

        model.addAttribute("items", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("message", message);

        return "warehouse";
    }
    //restock method, will bring you to add items into database


}
