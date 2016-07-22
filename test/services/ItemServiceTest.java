package services;

import entities.Item;
import org.junit.Before;

import static org.mockito.Mockito.mock;
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

    private ItemService itemSrvMock;
    @Inject
    private ItemService itemSrv;

    @Before
    public void beforeTest() {
        itemSrvMock = mock(ItemService.class);
    }


    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertThat(a).isEqualTo(2);
    }

    /**
     * Test listing items
     */
    @Test
    public void listItemsTest() {
        List<Item> items = itemSrv.getAllItems();
        int curCount = items.size();

        Item item1 = new Item("item1","item1 description");
        itemSrv.addItem(item1);

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount+1).isEqualTo(newCount);
    }

    /**
     * Test adding items
     */
    @Test
    public void addItemTest() {
        Item item = new Item("item2","item2 description");
        itemSrv.addItem(item);

        assertThat(itemSrv.getAllItems().get(0).equals(item));
    }

    /**
     * Test removing items
     */
    @Test
    public void removeItemTest() {
        Item item1 = new Item("item1","item1 description");
        itemSrv.addItem(item1);

        List<Item> items = itemSrv.getAllItems();
        int curCount = items.size();

        itemSrv.removeItemById(item1.getId());

        items = itemSrv.getAllItems();
        int newCount = items.size();

        assertThat(curCount-1).isEqualTo(newCount);
    }
}
