package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StockRepository {

    Optional<StockItem> findById(String id);

    List<StockItem> findByIdIn(Set<String> ids);

    void save(StockItem stockItem);
}
