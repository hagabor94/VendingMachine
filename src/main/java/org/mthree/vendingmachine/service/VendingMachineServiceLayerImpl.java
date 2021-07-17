package org.mthree.vendingmachine.service;

import org.mthree.vendingmachine.dao.VendingMachineAuditDao;
import org.mthree.vendingmachine.dao.VendingMachineDao;
import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    @Autowired
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public BigDecimal getItem(int id, BigDecimal funds) throws VendingMachinePersistenceException, VendingMachineIsufficientFundsException, VendingMachineNoItemInventoryException, VendingMachineNoSuchItemException {
        Item requestedItem = dao.getItem(id);

        if(requestedItem == null){
            throw new VendingMachineNoSuchItemException("NO SUCH ITEM");
        }

        if(requestedItem.getQuantity() == 0){
            throw new VendingMachineNoItemInventoryException("REQUESTED ITEM IS EMPTY");
        }

        if(funds.compareTo(requestedItem.getPrice()) == -1){
            throw new VendingMachineIsufficientFundsException("REQUESTED ITEM COSTS MORE");
        }

        auditDao.writeAuditEntry("ITEM REQUESTED : "+requestedItem.getName());

        editItem(id);

        return funds.subtract(requestedItem.getPrice());
    }

    @Override
    public Item editItem(int id) throws VendingMachinePersistenceException {
        Item editedItem = dao.editItem(id);
        auditDao.writeAuditEntry("ITEM SERVED : "+editedItem.getName()+" QUANTITY : "+editedItem.getQuantity());
        return editedItem;
    }

    @Override
    public Map<Coins, Integer> changeToCoins(BigDecimal change) {
        BigDecimal remainingChange = new BigDecimal(String.valueOf(change));
        Map<Coins, Integer> coins = new HashMap<>();
        int coinCounter = 0;
        while( !(BigDecimal.valueOf(1).compareTo(remainingChange.divide(Coins.QUARTER.getValue())) == 1)){
            coinCounter++;
            remainingChange = remainingChange.subtract(Coins.QUARTER.getValue());
        }
        if(coinCounter > 0)
            coins.put(Coins.QUARTER, coinCounter);

        coinCounter = 0;
        while( !(BigDecimal.valueOf(1).compareTo(remainingChange.divide(Coins.DIME.getValue())) == 1)){
            coinCounter++;
            remainingChange = remainingChange.subtract(Coins.DIME.getValue());
        }
        if(coinCounter > 0)
            coins.put(Coins.DIME, coinCounter);

        coinCounter = 0;
        while( !(BigDecimal.valueOf(1).compareTo(remainingChange.divide(Coins.NICKEL.getValue())) == 1)){
            coinCounter++;
            remainingChange = remainingChange.subtract(Coins.NICKEL.getValue());
        }
        if(coinCounter > 0)
            coins.put(Coins.NICKEL, coinCounter);

        coinCounter = 0;
        while( !(BigDecimal.valueOf(1).compareTo(remainingChange.divide(Coins.PENNY.getValue())) == 1)){
            coinCounter++;
            remainingChange = remainingChange.subtract(Coins.PENNY.getValue());
        }
        if(coinCounter > 0)
            coins.put(Coins.PENNY, coinCounter);


        return coins;
    }
}












