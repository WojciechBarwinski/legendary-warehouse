package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StockRepository {

    Optional<StockItem> findById(Long id);

    List<StockItem> findByIdIn(Set<Long> ids);

    void save(StockItem stockItem);
}
