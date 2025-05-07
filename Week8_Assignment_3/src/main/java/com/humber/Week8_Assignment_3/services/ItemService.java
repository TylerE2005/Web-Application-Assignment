package com.humber.Week8_Assignment_3.services;

import com.humber.Week8_Assignment_3.models.Item;
import com.humber.Week8_Assignment_3.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //Marks as a Service Component and which will indicate that the class contains business logic
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {return itemRepository.findAll();}

    //Saves item into the Database table
    public int saveItem(Item item) {
        if (item.getPrice() <= 1000 || item.getCreationDate() <= 2021){
            return 0;
        }
        itemRepository.save(item);
        return 1;
    }

    //Will Try to delete the item by it's id if it exists
    public int deleteItemByID(int id) {
        if(itemRepository.existsById(id)){
            itemRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    //Will update the item in the database
    public void updateItem(Item item) {itemRepository.save(item);}

    //Will determine if item is present in the Database by its ID
    public Optional<Item> getItemById(int id) {return itemRepository.findById(id);}


    public List<Item> getItemsSortedByBrand() {
        return itemRepository.findAllByOrderByBrandAsc();
    }

    //Will paginate the warehouse page so there's only 5 items present at a time on the page, sorted by its id and ascending value
    public Page<Item> getPaginatedSortedItems(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending():Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return itemRepository.findAll(pageable);
    }

    //Will sort the paginated page by Brand allows to be toggled to ascending or descending order
    public Page<Item> getPaginatedSortedItemsByBrand(int pageNo, int pageSize, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("brand").ascending()
                : Sort.by("brand").descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return itemRepository.findAll(pageable);
    }
}
