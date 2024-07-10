package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class StockItem {

    private Product product;
    private int quantity;
}
