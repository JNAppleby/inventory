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

    /**
     * Edit an existing item
     * @param id id of item to edit
     * @param name edited name
     * @param desc edited description
     * @return
     */
    Item editItem(Long id, String name, String desc);
}
