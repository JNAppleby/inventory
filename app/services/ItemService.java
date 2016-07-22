package services;

import entities.Item;

import java.util.List;

public interface ItemService {
    /**
     * Get all inventory items from db
     * @return list of all items from db
     */
    List<Item> getAllItems();

    /**
     * Add an inventory item
     * @param item an item to add
     * @return original item on success, null on error
     */
    Item addItem(Item item);

    /**
     * Delete an item from the database by id
     * @param id the id of an item to delete
     */
    void deleteItemById(Long id);
}
