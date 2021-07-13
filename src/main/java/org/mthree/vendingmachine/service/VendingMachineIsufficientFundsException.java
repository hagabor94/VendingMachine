package org.mthree.vendingmachine.service;

public class VendingMachineIsufficientFundsException extends Exception {
    public VendingMachineIsufficientFundsException(String message){super(message);}

    public VendingMachineIsufficientFundsException(String message,Throwable cause){super(message, cause);}
}
