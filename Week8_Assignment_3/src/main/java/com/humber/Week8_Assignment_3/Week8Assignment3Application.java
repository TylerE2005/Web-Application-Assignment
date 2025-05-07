package com.humber.Week8_Assignment_3;

import com.humber.Week8_Assignment_3.models.Item;
import com.humber.Week8_Assignment_3.services.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //CommandLineRunner allows this class to execute logic after the application starts
public class Week8Assignment3Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Week8Assignment3Application.class, args);
	}


	private final ItemService itemService;

	//allows use for itemService and it's Methods
	public Week8Assignment3Application(ItemService itemService) {
		this.itemService = itemService;
	}

	@Override //This method runs after the application started, and used to populate the datebase with items
	public void run(String... args) throws Exception {
		itemService.saveItem(new Item("Shoes", "Adidas", 2023, 1500.0));
		itemService.saveItem(new Item("Sweater", "Nike", 2022, 1199.0));
		itemService.saveItem(new Item("Shorts", "Puma", 2024, 1299.0));
		itemService.saveItem(new Item("Hat", "Adidas", 2022, 1350.0));
		itemService.saveItem(new Item("Socks", "Nike", 2025, 1050.0));
		itemService.saveItem(new Item("Shirt", "Polo", 2022, 1700.0));
		itemService.saveItem(new Item("Jacket", "Nike", 2023, 1899.0));
	}

}
