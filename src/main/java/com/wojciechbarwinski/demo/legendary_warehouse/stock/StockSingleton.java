package com.wojciechbarwinski.demo.legendary_warehouse.stock;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class StockSingleton {

    private static StockSingleton instance;
    private List<StockItem> stockItemList;

    private StockSingleton() {
        stockItemList = new ArrayList<>();
        addProductsOnStart();
    }

    public static StockSingleton getInstance() {
        if (instance == null) {
            instance = new StockSingleton();
        }
        return instance;
    }

    private void addProductsOnStart() {
        stockItemList.add(new StockItem(new Product("001B", "Gloomheaven"), 10));
        stockItemList.add(new StockItem(new Product("002B", "Tainted Grail"), 5));
        stockItemList.add(new StockItem(new Product("003M", "The Old World: Tomb Guards"), 12));
        stockItemList.add(new StockItem(new Product("004M", "The Old World: NeckroSphinx"), 2));
        stockItemList.add(new StockItem(new Product("005B", "Tainted Grail: Last Knight"), 7));
        stockItemList.add(new StockItem(new Product("006B", "Descent: Legend of Darkness"), 1));
        stockItemList.add(new StockItem(new Product("007A", "Red dices set"), 10));
        stockItemList.add(new StockItem(new Product("008A", "Black dices set"), 10));
        stockItemList.add(new StockItem(new Product("009A", "small Paintbrush"), 6));
        stockItemList.add(new StockItem(new Product("010M", "The Old World: Tomb Kings Chariot"), 4));
    }
}
