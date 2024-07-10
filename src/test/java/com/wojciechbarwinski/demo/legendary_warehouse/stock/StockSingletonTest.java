package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StockSingletonTest {


    @Test
    public void testCorrectStockSingletonInitializationWithData() {
        int expectedStockSize = 10;
        int indexOfLastItem = 9;
        String lastItemId = "010M";

        StockSingleton stock = StockSingleton.getInstance();
        Product lastProductFromStock = stock.getStockItemList().get(indexOfLastItem).getProduct();

        Assertions.assertEquals(expectedStockSize, stock.getStockItemList().size());
        Assertions.assertEquals(lastItemId, lastProductFromStock.getId());
    }
}