package org.mthree.vendingmachine.controller;

import org.mthree.vendingmachine.dao.VendingMachinePersistenceException;
import org.mthree.vendingmachine.service.VendingMachineIsufficientFundsException;
import org.mthree.vendingmachine.service.VendingMachineNoItemInventoryException;
import org.mthree.vendingmachine.service.VendingMachineNoSuchItemException;
import org.mthree.vendingmachine.service.VendingMachineServiceLayer;
import org.mthree.vendingmachine.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VendingMachineController {
    private VendingMachineView view;
    private VendingMachineServiceLayer service;

    @Autowired
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.service = service;
        this.view = view;
    }

    public void run(){
        boolean keepGoing = true;
        try{
            while(keepGoing){
                view.printMenu(service.getAllItems());

                BigDecimal funds = view.getFunds();
                try {
                    int choice = view.getItemId();
                    view.displayChange(service.changeToCoins(service.getItem(choice,funds)));
                } catch (VendingMachineNoItemInventoryException | VendingMachineIsufficientFundsException | VendingMachineNoSuchItemException e){
                    view.displayError(e.getMessage());
                }
                finally {
                    keepGoing = wishToContinue();
                }

            }
        }catch (VendingMachinePersistenceException e){
            view.displayError(e.getMessage());
        }
    }

    private Boolean wishToContinue(){
        String yOrN = "";
        do{
            yOrN = view.getContinue().toLowerCase();
            if(yOrN.toLowerCase().equals("y")){
                return true;
            }
            else if(yOrN.toLowerCase().equals("n")){
                return false;
            }
            else{
                view.displayUnrecognisedInput();
            }

        }while(yOrN.equals("y") || yOrN.equals("n"));
        return true;
    }



}
