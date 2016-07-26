package services;

import errcodes.ErrCode;

import conf.AppConf;
import conf.TestDataConf;
import entities.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import javax.inject.Inject;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class ItemServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject private ItemService itemSrv;

    /**
     * Listing of empty set of <@link Item>s
     */
    @Test
    public void listEmptySetTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);
        Assert.assertEquals("Wrong size of the list, should be 0", 0, items.size());
        Assert.assertTrue("Items list should be empty", items.isEmpty());
    }

    /**
     * List a single item
     */
    @Test
    public void listSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Item adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item1));
        items = itemSrv.getAllItems();

        Assert.assertEquals("Total item count should be 1", 1, items.size());
        Assert.assertNotNull("Item retrieved is null", items.get(0));
    }

    /**
     * List two items
     */
    @Test
    public void listTwoItemsTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item = new Item("item1", "item1 description");
        Assert.assertEquals("Item 1 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        item = new Item("item2", "item2 description");
        Assert.assertEquals("Item 2 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        Assert.assertNotEquals("Items in the list are not unique, but should have been.", items.get(0), items.get(1));
    }

    /**
     * Single item addition
     */
    @Test
    public void addSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item = new Item("item1", "item1 description");
        Assert.assertEquals("Item adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        Assert.assertEquals("Retrieved item is not the same as original", item, items.get(0));
    }

    /**
     * Add same/exact duplicate item
     */
    @Test
    public void addExactDupItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item = new Item("item1", "item1 description");
        Assert.assertEquals("Item adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add same exact/original/duplicate item
        Assert.assertEquals("Duplicate item protection fails", ErrCode.ADD_DUPLICATE_ITEM, itemSrv.addItem(item));
        Assert.assertEquals("Total item count should still be 1 after adding duplicate item", 1, itemSrv.getAllItems().size());
        Assert.assertEquals("Retrieved item is not the same as original", item, items.get(0));
    }

    /**
     * Add different item, but with duplicate Name
     */
    @Test
    public void addDiffItemDupNameTest() {
        final String itemName = "item1";
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item = new Item(itemName, "item1 description");
        Assert.assertEquals("Item adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add different item, but with duplicate Name
        final Item diffItemSameName = new Item(itemName, "different description");
        itemSrv.addItem(diffItemSameName);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1 after addition of different item which has duplicate Name", 1, items.size());

        final Item retrievedItem = items.get(0);

        Assert.assertEquals("Retrieved item is not the same as original", item, retrievedItem);
        Assert.assertNotEquals("Retrieved item should NOT be the same as duplicate item that was attempted to be inserted",
                               diffItemSameName, retrievedItem);
    }

    /**
     * Add null-item
     */
    @Test
    public void addNullItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);
        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Assert.assertEquals("ADD_NULL_ITEM error should have been returned", ErrCode.ADD_NULL_ITEM, itemSrv.addItem(null));

        Assert.assertEquals("Total item count should be still 0", 0, itemSrv.getAllItems().size());
    }

    /**
     * Add blank item
     */
    @Test
    public void addBlankItemTest() {
        Item item = new Item();

        Assert.assertEquals("Should have gotten ADD_NAME_MIN_LEN error", ErrCode.ADD_NAME_MIN_LEN, itemSrv.addItem(item));
    }

    /**
     * Add two items
     */
    @Test
    public void addTwoDiffItemsTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        int initialCount = items.size();
        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, initialCount);

        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Item 1 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item1));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add second item
        final Item item2 = new Item("item2", "item2 description");
        Assert.assertEquals("Item 2 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item2));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        Assert.assertEquals("Retrieved item1 is not the same as original", item1, items.get(0));
        Assert.assertEquals("Retrieved item2 is not the same as original", item2, items.get(1));
    }

    /**
     * Remove single item
     */
    @Test
    public void removeSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Item 1 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item1));

        items = itemSrv.getAllItems();
        Assert.assertEquals("One item added, total item count should be 1 now", 1, items.size());

        Item retrievedItem1 = items.get(0);
        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);

        Assert.assertEquals("Error removing an item", ErrCode.REMOVE_SUCCESS, itemSrv.removeItemById(retrievedItem1.getId()));
        Assert.assertEquals("Item was removed, count should be back to 0", 0, itemSrv.getAllItems().size());
    }

    /**
     * Remove non-existing item
     */
    @Test
    public void removeNonExistingItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Assert.assertEquals("Should have returned REMOVE_NON_EXISTENT code", ErrCode.REMOVE_NON_EXISTENT, itemSrv.removeItemById(42L));
        Assert.assertEquals("Non-existing item deletion was attempted, count should be still 0", 0, itemSrv.getAllItems().size());
    }

    /**
     * Remove item with negative Id
     */
    @Test
    public void removeNegativeIdItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Assert.assertEquals("Should have returned REMOVE_NON_EXISTENT code", ErrCode.REMOVE_NON_EXISTENT, itemSrv.removeItemById(-1L));
        Assert.assertEquals("Negative and non-existing item deletion was attempted, count should be still 0", 0, itemSrv.getAllItems().size());
    }

    /**
     * Remove same item twice in the row
     */
    @Test
    public void removeSameItemTwiceTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Item 1 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item1));

        items = itemSrv.getAllItems();
        Assert.assertEquals("One item added, total item count should be 1 now", 1, items.size());

        final Item retrievedItem1 = items.get(0);
        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);

        Assert.assertEquals("Error removing an item", ErrCode.REMOVE_SUCCESS, itemSrv.removeItemById(retrievedItem1.getId()));
        Assert.assertEquals("Item was removed, count should be back to 0", 0, itemSrv.getAllItems().size());

        // Removing the same exact item
        Assert.assertEquals("Should return REMOVE_NON_EXISTENT code", ErrCode.REMOVE_NON_EXISTENT, itemSrv.removeItemById(retrievedItem1.getId()));
        Assert.assertEquals("Items count should be still 0 after removing the only item twice in the row", 0, itemSrv.getAllItems().size());
    }

    /**
     * Remove two items
     */
    @Test
    public void removeTwoItemsTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        int initialCount = items.size();
        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, initialCount);

        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Item 1 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item1));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add second item
        final Item item2 = new Item("item2", "item2 description");
        Assert.assertEquals("Item 2 adding failure", ErrCode.ADD_SUCCESS, itemSrv.addItem(item2));

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        final Item retrievedItem1 = items.get(0);
        final Item retrievedItem2 = items.get(1);

        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);
        Assert.assertEquals("Retrieved item2 is not the same as original", item2, retrievedItem2);

        // Remove first item
        Assert.assertEquals("Error removing 1st item", ErrCode.REMOVE_SUCCESS, itemSrv.removeItemById(retrievedItem1.getId()));
        items = itemSrv.getAllItems();
        Assert.assertEquals("One of two items was removed, count should be 1", 1, items.size());

        // lastRemainingItem should be the second inserted item
        Assert.assertEquals("Retrieved item2 is not the same as original", item2, items.get(0));

        // Remove second item
        Assert.assertEquals("Error removing 2nd item", ErrCode.REMOVE_SUCCESS, itemSrv.removeItemById(retrievedItem2.getId()));
        Assert.assertEquals("Last item was removed from db, count should be 0", 0, itemSrv.getAllItems().size());
    }
}
