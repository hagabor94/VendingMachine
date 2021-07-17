package org.mthree.vendingmachine.dao;

import org.mthree.vendingmachine.dto.Item;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Component
public class VendingMachineDaoFileImpl implements VendingMachineDao{

    private final String ITEMS_FILE;
    public static final String DELIMETER = "::";
    private Map<Integer,Item> items = new HashMap<>();

    public VendingMachineDaoFileImpl(){ITEMS_FILE = "items.txt";}

    public VendingMachineDaoFileImpl(String fileName){ITEMS_FILE = fileName;}

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadItems();
        return new ArrayList<Item>(items.values());
    }

    @Override
    public Item getItem(int id) throws VendingMachinePersistenceException {
        loadItems();
        return items.get(id);
    }

    @Override
    public Item editItem(int id) throws VendingMachinePersistenceException {
        loadItems();
        Item editedItem = getItem(id);
        editedItem.setQuantity(editedItem.getQuantity()-1);
        items.put(id, editedItem);
        writeItems();
        return editedItem;
    }

    @Override
    public Item addItem(Item item) throws VendingMachinePersistenceException{
        loadItems();
        Item addedItem = items.put(item.getId(), item);
        writeItems();
        return addedItem;
    }

    private Item unmarshallItem(String itemAsText){
        String[] itemTokens = itemAsText.split(DELIMETER);
        Item itemFromFile = new Item(Integer.parseInt(itemTokens[0]),itemTokens[1],new BigDecimal(itemTokens[2]),Integer.parseInt(itemTokens[3]));

        return itemFromFile;
    }

    private void loadItems() throws VendingMachinePersistenceException{
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(ITEMS_FILE)));
        }catch (FileNotFoundException e){
            throw new VendingMachinePersistenceException("Couldn't load data from file", e);
        }

        String currentLine;
        Item currentItem;

        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentItem = unmarshallItem(currentLine);
            items.put(currentItem.getId(),currentItem);
        }
        scanner.close();
    }

    private String marshallItem(Item item){
        String itemAsText = item.getId()+DELIMETER;
        itemAsText += item.getName()+DELIMETER;
        itemAsText += item.getPrice()+DELIMETER;
        itemAsText += item.getQuantity();

        return itemAsText;
    }

    private void writeItems() throws VendingMachinePersistenceException{
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(ITEMS_FILE));
        }catch (IOException e){
            throw new VendingMachinePersistenceException("Couldn't save data",e);
        }

        String itemAsText;
        List<Item> itemList = this.getAllItems();

        for(Item item : itemList){
            itemAsText = marshallItem(item);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }
}
