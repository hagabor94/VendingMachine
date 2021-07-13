package org.mthree.vendingmachine.dao;

import org.mthree.vendingmachine.dto.Item;

import java.util.List;

public interface VendingMachineDao {
    List<Item> getAllItems() throws VendingMachinePersistenceException;

    Item getItem(int id) throws VendingMachinePersistenceException;

    Item editItem(int id) throws VendingMachinePersistenceException;

    Item addItem(Item item) throws VendingMachinePersistenceException;
}
