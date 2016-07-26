package entities;

import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    /**
     * Test equals with exact same item
     */
    @Test
    public void equalsSameExactItemTest() {
        final Item item1 = new Item("item1", "item1 description");
        Assert.assertEquals("Items are not the same", item1, item1);
    }

    /**
     * Test equals with equivalent items
     */
    @Test
    public void equalsEquivalentItemsTest() {
        final String itemName = "item1";
        final String itemDesc = "item1 description";

        final Item item1 = new Item(itemName, itemDesc);
        final Item item2 = new Item(itemName, itemDesc);

        Assert.assertEquals("Items should be the same", item1, item2);
    }

    /**
     * Test equals with different items
     */
    @Test
    public void equalsDiffItemsTest() {
        final Item item1 = new Item("item1", "item1 desc");
        final Item item2 = new Item("item2", "item2 desc");

        Assert.assertNotEquals("Items are evaluated to be the same, while they are not", item1, item2);
    }

    /**
     * Test equals with null description
     */
    @Test
    public void equalsNullDescTest() {
        final String itemName = "item1";

        final Item item1 = new Item(itemName, null);
        final Item item2 = new Item(itemName, null);

        Assert.assertEquals("Items are not the same", item1, item2);
    }

    /**
     * Same Name, but different Description
     * Items should be different
     */
    @Test
    public void equalsSameNameDiffDescTest() {
        final String itemName = "item1";

        final Item item1 = new Item(itemName, "diff description 1");
        final Item item2 = new Item(itemName, "diff description 2");

        Assert.assertNotEquals("Items should not be the same", item1, item2);
    }
}
