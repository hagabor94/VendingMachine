package org.mthree.vendingmachine.service;

import org.mthree.vendingmachine.dao.VendingMachineAuditDao;
import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;

public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {

    }
}
