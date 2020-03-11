package com.dong.util;


import com.dong.entry.Good;


public class CarItem {
    private Integer quantity;
    private Good good;
    private double money;
    
    public CarItem() {
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
    public double getMoney() {
        this.money=this.good.getPrice()*quantity;
        return money;
    }

    

   
    
    
}
