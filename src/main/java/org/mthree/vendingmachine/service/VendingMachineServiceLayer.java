package org.mthree.vendingmachine.service;

import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface VendingMachineServiceLayer {
    List<Item> getAllItems() throws VendingMachinePersistenceException;

    BigDecimal getItem(int id, BigDecimal funds) throws VendingMachinePersistenceException,
            VendingMachineIsufficientFundsException,
            VendingMachineNoItemInventoryException,
            VendingMachineNoSuchItemException;

    Item editItem(int id) throws VendingMachinePersistenceException;

    Map<Coins, Integer> changeToCoins(BigDecimal change);
}
