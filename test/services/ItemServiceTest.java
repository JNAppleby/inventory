package services;

import conf.AppConf;
import conf.TestDataConf;
import entities.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import javax.inject.Inject;

@ContextConfiguration(classes = {
    AppConf.class, TestDataConf.class
})
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
    }

    /**
     * List a single item
     */
    @Test
    public void listSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);
        items = itemSrv.getAllItems();

        Item retrievedItem = items.get(0);

        Assert.assertEquals("Total item count should be 1", 1, items.size());
        Assert.assertNotNull("Item retrieved is null", retrievedItem);
        Assert.assertNotNull("Item Name of retrieved element is null", retrievedItem.getName());
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
        itemSrv.addItem(item);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        item = new Item("item2", "item2 description");
        itemSrv.addItem(item);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        Item retrievedItem1 = items.get(0);
        Item retrievedItem2 = items.get(1);

        Assert.assertNotEquals("Items in the list are not unique, but should have been.", retrievedItem1, retrievedItem2);
        Assert.assertNotEquals("Names of the items in the list are not unique, but should have been.", retrievedItem1.getName(),
                               retrievedItem2.getName());
    }

    /**
     * Single item addition
     */
    @Test
    public void addSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item = new Item("item1", "item1 description");
        itemSrv.addItem(item);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        Item retrievedItem = items.get(0);

        Assert.assertEquals("Retrieved item is not the same as original", item, retrievedItem);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem.getDesc());
    }

    /**
     * Add duplicate item
     */
    @Test
    public void addDuplicateItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item = new Item("item1", "item1 description");
        itemSrv.addItem(item);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add same exact/original/duiplicate item
        itemSrv.addItem(item);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should still be 1 after adding duplicate item", 1, items.size());

        // Add different item, but with duplicate Name
        Item diffItemSameName = new Item("item1", "different description");
        itemSrv.addItem(diffItemSameName);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1 after addition of different item which has duplicate Name", 1, items.size());

        Item retrievedItem = items.get(0);

        Assert.assertEquals("Retrieved item is not the same as original", item, retrievedItem);
        Assert.assertNotEquals("Retrieved item should NOT be the same as duplicate item that was attempted to be inserted",
                               diffItemSameName, retrievedItem);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem.getDesc());
    }

    /**
     * Add null-item
     */
    @Test
    public void addNullItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        itemSrv.addItem(null);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be still 0", 0, items.size());
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

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add second item
        Item item2 = new Item("item2", "item2 description");
        itemSrv.addItem(item2);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        Item retrievedItem1 = items.get(0);
        Item retrievedItem2 = items.get(1);

        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem1.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem1.getDesc());

        Assert.assertEquals("Retrieved item2 is not the same as original", item2, retrievedItem2);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item2", retrievedItem2.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item2 description", retrievedItem2.getDesc());
    }

    /**
     * Remove single item
     */
    @Test
    public void removeSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        Assert.assertEquals("One item added, total item count should be 1 now", 1, items.size());

        Item retrievedItem1 = items.get(0);
        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem1.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem1.getDesc());

        itemSrv.removeItemById(retrievedItem1.getId());
        items = itemSrv.getAllItems();
        Assert.assertEquals("Item was removed, count should be back to 0", 0, items.size());
    }

    /**
     * Remove non-existing item
     */
    @Test
    public void removeNonExistingItemTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        itemSrv.removeItemById(-42L);
        items = itemSrv.getAllItems();
        Assert.assertEquals("Non-existing item deletion was attempted, count should be still 0", 0, items.size());
    }

    /**
     * Remove same item twice in the row
     */
    @Test
    public void removeSameItemTwiceTest() {
        List<Item> items = itemSrv.getAllItems();
        Assert.assertNotNull("List of items is null", items);

        Assert.assertEquals("Wrong initial size of the list, should be 0", 0, items.size());

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        Assert.assertEquals("One item added, total item count should be 1 now", 1, items.size());

        Item retrievedItem1 = items.get(0);
        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem1.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem1.getDesc());

        itemSrv.removeItemById(retrievedItem1.getId());
        items = itemSrv.getAllItems();
        Assert.assertEquals("Item was removed, count should be back to 0", 0, items.size());

        // Removing the same exact item
        itemSrv.removeItemById(retrievedItem1.getId());
        items = itemSrv.getAllItems();
        Assert.assertEquals("Items count should be still 0 after removing the only item twice in the row", 0, items.size());
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

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 1", 1, items.size());

        // Add second item
        Item item2 = new Item("item2", "item2 description");
        itemSrv.addItem(item2);

        items = itemSrv.getAllItems();
        Assert.assertEquals("Total item count should be 2", 2, items.size());

        Item retrievedItem1 = items.get(0);
        Item retrievedItem2 = items.get(1);

        Assert.assertEquals("Retrieved item1 is not the same as original", item1, retrievedItem1);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item1", retrievedItem1.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item1 description", retrievedItem1.getDesc());

        Assert.assertEquals("Retrieved item2 is not the same as original", item2, retrievedItem2);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item2", retrievedItem2.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item2 description", retrievedItem2.getDesc());

        // Remove first item
        itemSrv.removeItemById(retrievedItem1.getId());
        items = itemSrv.getAllItems();
        Assert.assertEquals("One of two items was removed, count should be 1", 1, items.size());

        Item lastRemainingItem = items.get(0);
        // lastRemainingItem should be the second inserted item
        Assert.assertEquals("Retrieved item2 is not the same as original", item2, lastRemainingItem);
        Assert.assertEquals("Retrieved item's Name is not the same as saved", "item2", lastRemainingItem.getName());
        Assert.assertEquals("Retrieved item's Description is not the same as saved", "item2 description", lastRemainingItem.getDesc());

        // Remove second item
        itemSrv.removeItemById(retrievedItem2.getId());
        items = itemSrv.getAllItems();
        Assert.assertEquals("Last item was removed from db, count should be 0", 0, items.size());
    }
}
