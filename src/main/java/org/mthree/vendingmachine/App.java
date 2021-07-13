package org.mthree.vendingmachine;

import org.mthree.vendingmachine.controller.VendingMachineController;
import org.mthree.vendingmachine.dao.VendingMachineAuditDao;
import org.mthree.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import org.mthree.vendingmachine.dao.VendingMachineDao;
import org.mthree.vendingmachine.dao.VendingMachineDaoFileImpl;
import org.mthree.vendingmachine.service.VendingMachineServiceLayer;
import org.mthree.vendingmachine.service.VendingMachineServiceLayerImpl;
import org.mthree.vendingmachine.ui.UserIO;
import org.mthree.vendingmachine.ui.UserIOConsoleImpl;
import org.mthree.vendingmachine.ui.VendingMachineView;

public class App {
    public static void main(String[] args) {
        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao audit = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao,audit);
        VendingMachineController controller = new VendingMachineController(service,view);

        controller.run();
    }
}
