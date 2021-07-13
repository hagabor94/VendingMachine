package org.mthree.vendingmachine.ui;

import org.mthree.vendingmachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {
    private UserIO io;

    public VendingMachineView(UserIO io){this.io = io;}

    public void printMenu(List<Item> itemList){
        itemList.stream().forEach(
                (i) -> io.print(i.getId()+" - "+i.getName()+" - $"+i.getPrice())
        );
        io.print("");
    }

    public int getItemId(){
        return io.readInt("Choose an item: ");
    }

    public BigDecimal getFunds(){
        return new BigDecimal(io.readString("How much money do you put in? ($)"));
    }

    public void displayChange(BigDecimal change){
        io.print("Change: $"+change);
    }

    public void displayError(String message){io.print("ERROR! "+message);}

    public void displayUnrecognisedInput(){
        io.print("Unrecognised input!");
    }

    public String getContinue(){ return io.readString("Do you wish to continue? Y/N");}
}
