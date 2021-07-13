package org.mthree.vendingmachine.service;

public class VendingMachineNoSuchItemException extends Exception{
    public VendingMachineNoSuchItemException(String message){super(message);}

    public VendingMachineNoSuchItemException(String message,Throwable cause){super(message, cause);}
}
