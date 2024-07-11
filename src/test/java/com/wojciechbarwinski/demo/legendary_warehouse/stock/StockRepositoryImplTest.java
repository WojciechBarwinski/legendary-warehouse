package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@SpringBootTest()
class StockRepositoryImplTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void shouldCheckIfStartingDataAreInDataBase() {
        String existingItemId = "010M";

        Optional<StockItem> itemFromDB = stockRepository.findById(existingItemId);

        Assertions.assertTrue(itemFromDB.isPresent());
        Assertions.assertEquals(existingItemId, itemFromDB.get().getProduct().getId());
    }

    @Test
    public void shouldFindByIdWithCorrectAndIncorrectId() {
        String existingItemId = "010M";
        String nonExistingItemId = "99999";

        Optional<StockItem> existingItem = stockRepository.findById(existingItemId);
        Optional<StockItem> nonExistingItem = stockRepository.findById(nonExistingItemId);

        Assertions.assertTrue(existingItem.isPresent());
        Assertions.assertEquals(existingItemId, existingItem.get().getProduct().getId());
        Assertions.assertTrue(nonExistingItem.isEmpty());
    }


    @Test
    public void shouldSaveStockItemByIdWithDifferentQuantity(){
        int newQuantity = 3;
        String orderedProductId = "001B";
        StockItem itemToSave = new StockItem(new Product(orderedProductId, "Gloomheaven"), newQuantity);

        stockRepository.save(itemToSave);
        StockItem itemAfterSave = stockRepository.findById(orderedProductId).get();

        Assertions.assertEquals(newQuantity, itemAfterSave.getQuantity());
    }
}