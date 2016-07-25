package services.impl;

import conf.AppConf;
import conf.TestDataConf;
import entities.Item;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(classes = {AppConf.class, TestDataConf.class})
public class ItemServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    /**
     * Test equals with exact same item
     */
    @Test
    public void equalsSameExactItemTest() {
        Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Items are not the same", item1, item1);
    }

    /**
     * Test equals with equivalent items
     */
    @Test
    public void equalsEquivalentItemsTest() {
        final String itemName = "item1";
        final String itemDesc = "item1 description";

        Item item1 = new Item(itemName, itemDesc);
        Item item2 = new Item(itemName, itemDesc);

        Assert.assertEquals("Items are not the same", item1, item2);
    }

    /**
     * Test equals with different items
     */
    @Test
    public void equalsDiffItemsTest() {
        Item item1 = new Item("item1", "item1 desc");
        Item item2 = new Item("item2", "item2 desc");

        Assert.assertNotEquals("Items are evaluated to be the same, while they are not", item1, item2);
    }

    /**
     * Test equals with null description
     */
    @Test
    public void equalsNullDescTest() {
        final String itemName = "item1";

        Item item1 = new Item(itemName, null);
        Item item2 = new Item(itemName, null);

        Assert.assertEquals("Items are not the same", item1, item2);
    }
}
