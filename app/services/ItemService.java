package services;

import java.util.List;

import entities.Item;
public interface ItemService {
    /**
     * Get all inventory items from db
     * @return
     */
    List<Item> getAllItems();

    /**
     * Add an inventory item
     * @param item - item to add
     * @return original item on success, null on error
     */
    Item addItem(Item item);

    /**
     * Delete an item from the database by id
     * @param id
     * @return
     */
    boolean deleteItemById(Long id);
}
