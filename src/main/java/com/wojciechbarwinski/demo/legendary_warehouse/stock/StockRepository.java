package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import java.util.Optional;

public interface StockRepository {

    Optional<StockItem> findById(String id);

    void save(StockItem stockItem);
}
