package services;

import entities.Item;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import conf.TestDataConf;
import conf.AppConf;
import static org.fest.assertions.Assertions.*;

import java.util.List;

import javax.inject.Inject;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class ItemServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Inject private ItemService itemSrv;

    /**
     * Listing without any items
     */
    @Test
    public void listBlankListTest() {
        List<Item> items = itemSrv.getAllItems();
        assertThat(items).isNotNull();

        int curCount = items.size();

        assertThat(curCount).isEqualTo(0);
    }

    /**
     * Listing single item
     */
    @Test
    public void listSingleItemTest() {
        List<Item> items = itemSrv.getAllItems();
        assertThat(items).isNotNull();

        int curCount = items.size();

        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount + 1).isEqualTo(newCount);
        assertThat(items.get(0)).isNotNull();
    }

    /**
     * Listing 2 items
     */
    @Test
    public void listTwoItemsTest() {
        List<Item> items = itemSrv.getAllItems();
        int curCount = items.size();

        Item item1 = new Item("item1", "item1 description");
        Item item2 = new Item("item2", "item2 description");
        itemSrv.addItem(item1);
        itemSrv.addItem(item2);

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount + 2).isEqualTo(newCount);
        assertThat(items.get(0)).isNotNull();
        assertThat(items.get(1)).isNotNull();
        assertThat(items.get(0).getId()).isNotEqualTo(items.get(1).getId());
    }

    /**
     * Simple add, check if added (count) and saved element is not null
     */
    @Test
    public void addItemCountAndNotNullTest() {
        Item item = new Item("item1", "item1 description");
        itemSrv.addItem(item);

        assertThat(itemSrv.getAllItems().get(0)).isNotNull();
        assertThat(itemSrv.getAllItems().get(0).equals(item));
    }

    /**
     * Add duplicate item
     */
    @Test
    public void addExactDupItemTest() {
        int count = itemSrv.getAllItems().size();
        Item item = new Item("item1", "item1 description");

        itemSrv.addItem(item);

        // add duplicate item
        itemSrv.addItem(item);

        List<Item> items = itemSrv.getAllItems();

        assertThat(count + 1).isEqualTo(items.size());
    }

    /**
     * Add item with duplicate name, but different description
     * Only the first item should be persisted
     */
    @Test
    public void addDupDiffDescItemTest() {
        int count = itemSrv.getAllItems().size();
        Item item = new Item("item1", "item1 description");

        itemSrv.addItem(item);

        // duplicate name, different description
        Item item2 = new Item("item1", "different description");
        itemSrv.addItem(item2);

        List<Item> items = itemSrv.getAllItems();

        assertThat(count + 1).isEqualTo(items.size());
        assertThat(items.get(0).getName()).isEqualTo("item1");
        assertThat(items.get(0).getDesc()).isEqualTo("item1 description");
    }

    /**
     * Verify added item contents
     */
    @Test
    public void addItemVerifyContentsTest() {
        Item item = new Item("item1", "item1 description");
        itemSrv.addItem(item);

        List<Item> items = itemSrv.getAllItems();

        assertThat(items.get(0).getName()).isEqualTo("item1");
        assertThat(items.get(0).getDesc()).isEqualTo("item1 description");
    }

    /**
     * Adding null
     */
    @Test
    public void addNullItemTest() {
        itemSrv.addItem(null);
        assertThat(itemSrv.getAllItems().size()).isEqualTo(0);
    }

    /**
     * Simple remove
     */
    @Test
    public void SimpleRemoveItemTest() {
        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        List<Item> items = itemSrv.getAllItems();
        int curCount = items.size();

        itemSrv.removeItemById(item1.getId());

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount - 1).isEqualTo(newCount);
    }

    /**
     * Non-existing item removal
     */
    @Test
    public void removeNonExistentItemTest() {
        // Non-existing id
        itemSrv.removeItemById(-10L);
    }

    /**
     * Repeated same item removal
     */
    @Test
    public void removeSameItemRepeatedlyTest() {
        Item item1 = new Item("item1", "item1 description");
        itemSrv.addItem(item1);

        List<Item> items = itemSrv.getAllItems();
        int curCount = items.size();

        itemSrv.removeItemById(item1.getId());

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount - 1).isEqualTo(newCount);

        // repeated deletion of the same item
        itemSrv.removeItemById(item1.getId());

        items = itemSrv.getAllItems();
        int repeatedRemovalCount = items.size();

        assertThat(repeatedRemovalCount).isEqualTo(newCount);
    }
}
