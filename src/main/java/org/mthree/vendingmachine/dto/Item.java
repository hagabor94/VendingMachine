package org.mthree.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public Item(int id, String name, BigDecimal price, int quantity){
        this.id=id;
        this.name=name;
        this.price=price;
        this.quantity=quantity;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode(){
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.price);
        hash = 89 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass())
            return false;

        final Item other = (Item)obj;

        if(!Objects.equals(this.id,other.id))
            return false;
        if(!Objects.equals(this.name,other.name))
            return false;
        if(!Objects.equals(this.price,other.price))
            return false;
        if(!Objects.equals(this.quantity,other.quantity))
            return false;

        return true;
    }
}
