package services;

import entities.Item;

import org.junit.Before;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import conf.TestDataConf;
import conf.AppConf;
import static org.fest.assertions.Assertions.*;

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
     * Test adding items
     */
    @Test
    public void testSimpleSave() {
        Item item = new Item("item1","item1 description");
        itemSrv.addItem(item);
        assertThat(itemSrv.getAllItems().get(0).equals(item));
    }

    /**
     *
     */

}
