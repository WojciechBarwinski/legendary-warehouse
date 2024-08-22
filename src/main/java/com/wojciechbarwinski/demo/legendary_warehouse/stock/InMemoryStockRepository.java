package com.wojciechbarwinski.demo.legendary_warehouse.stock;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Repository
public class InMemoryStockRepository implements StockRepository {

    private Map<Long, StockItem> productsMap;

    InMemoryStockRepository() {
        productsMap = new ConcurrentHashMap<>();
        addProductsOnStart();
    }


    private void addProductsOnStart() {
        productsMap.put(1L, new StockItem(new Product(1L, "Gloomheaven"), 10));
        productsMap.put(2L, new StockItem(new Product(2L, "Tainted Grail"), 5));
        productsMap.put(3L, new StockItem(new Product(3L, "The Old World: Tomb Guards"), 12));
        productsMap.put(4L, new StockItem(new Product(4L, "The Old World: NeckroSphinx"), 2));
        productsMap.put(5L, new StockItem(new Product(5L, "Tainted Grail: Last Knight"), 7));
        productsMap.put(6L, new StockItem(new Product(6L, "Descent: Legend of Darkness"), 1));
        productsMap.put(7L, new StockItem(new Product(7L, "Red dices set"), 10));
        productsMap.put(8L, new StockItem(new Product(8L, "Black dices set"), 10));
        productsMap.put(9L, new StockItem(new Product(9L, "small Paintbrush"), 6));
        productsMap.put(10L, new StockItem(new Product(10L, "The Old World: Tomb Kings Chariot"), 4));
    }

    @Override
    public Optional<StockItem> findById(Long id) {
        return Optional.ofNullable(productsMap.get(id));
    }

    @Override
    public List<StockItem> findByIdIn(Set<Long> ids) {
        List<StockItem> productsFromInMemoryDB = new ArrayList<>();

        for (Long id : ids) {
            StockItem stockItem = productsMap.get(id);
            if (stockItem != null){
                productsFromInMemoryDB.add(stockItem);
            }
        }

        return productsFromInMemoryDB;
    }

    @Override
    public void save(StockItem stockItem) {
        productsMap.put(stockItem.getProduct().getId(), stockItem);
    }
}
