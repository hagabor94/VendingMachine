package org.mthree.vendingmachine.service;

import org.junit.jupiter.api.Test;
import org.mthree.vendingmachine.dao.VendingMachineAuditDao;
import org.mthree.vendingmachine.dao.VendingMachineDao;
import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {

    private VendingMachineServiceLayer service;

    public VendingMachineServiceLayerImplTest(){
        /*VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao audit = new VendingMachineAuditDaoStubImpl();
        service = new VendingMachineServiceLayerImpl(dao,audit);*/

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);

    }

    @Test
    void getAllItems() throws Exception{
        Item testClone1 = new Item(1,"Chocolate",new BigDecimal(1),1);
        Item testClone2 = new Item(2,"Crackers",new BigDecimal(1),0);

        assertEquals(2,service.getAllItems().size(),"should be 2");
        assertTrue(service.getAllItems().contains(testClone1));
        assertTrue(service.getAllItems().contains(testClone2));
    }

    @Test
    void getItemSuccessfully()  {

        try {
            BigDecimal change = service.getItem(1,BigDecimal.valueOf(1));
            assertEquals(change,BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(1)),"Change should be 0");
        }catch (VendingMachineNoItemInventoryException|VendingMachineNoSuchItemException|VendingMachineIsufficientFundsException| VendingMachinePersistenceException e){
            fail("Valid item and transaction, no exceptions should be thrown");
        }

    }

    @Test
    void getItemNoSuchItem()  {

        try {
            BigDecimal change = service.getItem(4,BigDecimal.valueOf(1));
            fail("No such item exception should be thrown");
        }catch (VendingMachineNoItemInventoryException|VendingMachineIsufficientFundsException| VendingMachinePersistenceException e){
            fail("Wrong exception thrown");
        }catch (VendingMachineNoSuchItemException e){
            return;
        }
    }

    @Test
    void getItemNotEnoughFunds()  {

        try {
            BigDecimal change = service.getItem(1,BigDecimal.valueOf(0.01));
            fail("Not enough funds exception should be thrown");
        }catch (VendingMachineNoItemInventoryException| VendingMachineNoSuchItemException | VendingMachinePersistenceException e){
            fail("Wrong exception thrown");
        }catch (VendingMachineIsufficientFundsException e){
            return;
        }
    }

    @Test
    void getItemNoInventoryLeft()  {

        try {
            BigDecimal change = service.getItem(2,BigDecimal.valueOf(1));

            fail("No item left exception should be thrown");
        }catch (VendingMachineIsufficientFundsException| VendingMachineNoSuchItemException | VendingMachinePersistenceException e){
            fail("Wrong exception thrown");
        }catch (VendingMachineNoItemInventoryException  e){
            return;
        }
    }

    @Test
    void editItem() throws Exception {
        Item testClone = new Item(1,"Chocolate",new BigDecimal(1),0);
        Item actualItem = service.editItem(1);

        assertNotNull(actualItem,"actual item should be not null");
        assertEquals(testClone,actualItem,"items should be same");
    }
}