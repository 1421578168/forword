package com.dong.util;

import java.util.HashMap;
import java.util.Map;

public class Car {
    private Map<Integer,CarItem> car = new HashMap<>();

    public Map<Integer, CarItem> getCarMap() {
        return car;
    }

    public Car() {
    }
    public void addGood(CarItem carItem){
        Integer id = carItem.getGood().getId();
        CarItem item = car.get(id);
        if (item == null) {
            car.put(id , carItem);
        } else {
            item.setQuantity(item.getQuantity() + carItem.getQuantity());
        }
    }
    
    public void removeGood(Integer id){
        car.remove(id);
    }

    public void clearCar(){
        if (car!=null){
            car.clear();
        }
    }
}
