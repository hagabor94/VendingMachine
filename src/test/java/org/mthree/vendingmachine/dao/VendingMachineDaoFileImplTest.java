package org.mthree.vendingmachine.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mthree.vendingmachine.dto.Item;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {
    VendingMachineDao dao;

    public VendingMachineDaoFileImplTest(){}

    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testitems.txt";
        new FileWriter(testFile);
        dao = new VendingMachineDaoFileImpl(testFile);
    }


    @Test
    void getAllItems() throws Exception {
        Item firstItem = new Item(1,"Chocolate",new BigDecimal(1),1);
        dao.addItem(firstItem);

        Item secondItem = new Item(2,"Crackers",new BigDecimal(1),2);
        dao.addItem(secondItem);

        List<Item> items = dao.getAllItems();

        assertNotNull(items,"List not null");
        assertEquals(2,items.size(),"List size should be 2");

        assertTrue(dao.getAllItems().contains(firstItem),"chokolate in list");
        assertTrue(dao.getAllItems().contains(secondItem),"crackers in list");
    }

    @Test
    void getItem() throws Exception{
        Item item = new Item(1,"Chocolate",new BigDecimal(1),1);
        dao.addItem(item);

        Item retrieved = dao.getItem(item.getId());

        assertEquals(item.getId(),retrieved.getId(),"id equals");
        assertEquals(item.getName(),retrieved.getName(),"name equals");
        assertEquals(item.getPrice(),retrieved.getPrice(),"price equals");
        assertEquals(item.getQuantity(),retrieved.getQuantity(),"quantity equals");
    }

    @Test
    void editItem() throws Exception {
        Item item = new Item(1,"Chocolate",new BigDecimal(1),1);
        dao.addItem(item);

        Item editedItem = dao.editItem(item.getId());

        assertEquals(item.getId(),editedItem.getId(),"id equals");
        assertEquals(item.getName(),editedItem.getName(),"name equals");
        assertEquals(item.getPrice(),editedItem.getPrice(),"price equals");
        assertEquals(item.getQuantity()-1,editedItem.getQuantity(),"quantity is one less");
    }


}