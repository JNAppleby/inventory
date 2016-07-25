package services;

import errcodes.ErrCode;

import entities.Item;

import java.util.List;

public interface ItemService {
    /**
     * Get all inventory items from db
     * @return {@link List<Item>} of all items from db
     */
    List<Item> getAllItems();

    /**
     * Add an inventory item
     * @param {@link Item} to add
     * @return original {@link Item} on success, {@link null} on error
     */
    ErrCode addItem(Item item);

    /**
     * Delete an item from the database by id
     * @param {@link Long} id of an item to delete
     */
    ErrCode removeItemById(Long id);
}
