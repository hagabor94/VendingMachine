package org.mthree.vendingmachine.service;

import org.mthree.vendingmachine.dao.VendingMachineAuditDao;
import org.mthree.vendingmachine.dao.VendingMachineDao;
import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

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
}
