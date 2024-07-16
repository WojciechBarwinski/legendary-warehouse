package com.wojciechbarwinski.demo.legendary_warehouse.stock;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Repository
public class StockRepositoryImpl implements StockRepository {

    private Map<String, StockItem> productsMap;

    private StockRepositoryImpl() {
        productsMap = new ConcurrentHashMap<>();
        addProductsOnStart();
    }


    private void addProductsOnStart() {
        productsMap.put("001B", new StockItem(new Product("001B", "Gloomheaven"), 10));
        productsMap.put("002B", new StockItem(new Product("002B", "Tainted Grail"), 5));
        productsMap.put("003M", new StockItem(new Product("003M", "The Old World: Tomb Guards"), 12));
        productsMap.put("004M", new StockItem(new Product("004M", "The Old World: NeckroSphinx"), 2));
        productsMap.put("005B", new StockItem(new Product("005B", "Tainted Grail: Last Knight"), 7));
        productsMap.put("006B", new StockItem(new Product("006B", "Descent: Legend of Darkness"), 1));
        productsMap.put("007A", new StockItem(new Product("007A", "Red dices set"), 10));
        productsMap.put("008A", new StockItem(new Product("008A", "Black dices set"), 10));
        productsMap.put("009A", new StockItem(new Product("009A", "small Paintbrush"), 6));
        productsMap.put("010M", new StockItem(new Product("010M", "The Old World: Tomb Kings Chariot"), 4));
    }

    @Override
    public Optional<StockItem> findById(String id) {
        return Optional.ofNullable(productsMap.get(id));
    }

    @Override
    public void save(StockItem stockItem) {
        productsMap.put(stockItem.getProduct().getId(), stockItem);
    }
}
