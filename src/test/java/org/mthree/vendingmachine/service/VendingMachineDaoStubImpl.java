package org.mthree.vendingmachine.service;

import org.mthree.vendingmachine.dao.VendingMachineDao;
import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.dto.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public Item onlyItem;
    public Item itemWithZeroQuantity;

    public VendingMachineDaoStubImpl(){

        onlyItem = new Item(1,"Chocolate", BigDecimal.valueOf(1),1);
        itemWithZeroQuantity = new Item(2,"Crackers",BigDecimal.valueOf(1),0);
    }

    public VendingMachineDaoStubImpl(Item item){this.onlyItem = item;}

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> items = new ArrayList<>();
        items.add(onlyItem);
        items.add(itemWithZeroQuantity);
        return items;
    }

    @Override
    public Item getItem(int id) throws VendingMachinePersistenceException {
        if(onlyItem.getId() == id)
            return onlyItem;
        else if(itemWithZeroQuantity.getId() == id)
            return itemWithZeroQuantity;
        else
            return null;
    }

    @Override
    public Item editItem(int id) throws VendingMachinePersistenceException {
        if(id == onlyItem.getId()){
            return new Item(onlyItem.getId(),onlyItem.getName(),onlyItem.getPrice(),onlyItem.getQuantity()-1);
        }
        else
            return null;
    }

    @Override
    public Item addItem(Item item) throws VendingMachinePersistenceException {
        if(item.equals(onlyItem))
            return onlyItem;
        else if(item.equals(itemWithZeroQuantity))
            return itemWithZeroQuantity;
        else
            return null;
    }
}
