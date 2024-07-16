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
    public void shouldContainInitialData() {
        //given
        String existingItemId = "010M";

        //when
        Optional<StockItem> itemFromDB = stockRepository.findById(existingItemId);

        //then
        Assertions.assertTrue(itemFromDB.isPresent());
        Assertions.assertEquals(existingItemId, itemFromDB.get().getProduct().getId());
    }

    @Test
    public void shouldFindByIdWithCorrectAndIncorrectId() {
        //given
        String existingItemId = "010M";
        String nonExistingItemId = "99999";

        //when
        Optional<StockItem> existingItem = stockRepository.findById(existingItemId);
        Optional<StockItem> nonExistingItem = stockRepository.findById(nonExistingItemId);

        //then
        Assertions.assertTrue(existingItem.isPresent());
        Assertions.assertEquals(existingItemId, existingItem.get().getProduct().getId());
        Assertions.assertTrue(nonExistingItem.isEmpty());
    }


    @Test
    public void shouldSaveStockItemByIdWithDifferentQuantity(){
        //given
        int newQuantity = 3;
        String orderedProductId = "001B";
        StockItem itemToSave = new StockItem(new Product(orderedProductId, "Gloomheaven"), newQuantity);

        //when
        stockRepository.save(itemToSave);
        StockItem itemAfterSave = stockRepository.findById(orderedProductId).get();

        //then
        Assertions.assertEquals(newQuantity, itemAfterSave.getQuantity());
    }
}