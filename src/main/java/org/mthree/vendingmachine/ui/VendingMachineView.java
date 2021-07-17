package org.mthree.vendingmachine.ui;

import org.mthree.vendingmachine.dto.Item;
import org.mthree.vendingmachine.service.Coins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class VendingMachineView {
    private UserIO io;

    @Autowired
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

    public void displayChange(Map<Coins, Integer> coins){
        io.print("CHANGE :");
        Set<Coins> coinTypes = coins.keySet();

        for (Coins c : coinTypes){
            io.print(c.toString()+" : "+coins.get(c));
        }
    }

    public void displayError(String message){io.print("ERROR! "+message);}

    public void displayUnrecognisedInput(){
        io.print("Unrecognised input!");
    }

    public String getContinue(){ return io.readString("Do you wish to continue? Y/N");}
}
