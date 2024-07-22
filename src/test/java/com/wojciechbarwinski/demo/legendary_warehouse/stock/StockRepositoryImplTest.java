package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;


class StockRepositoryImplTest {

    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        stockRepository = new InMemoryStockRepository();
    }

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
    public void shouldFindByIdWithCorrectId() {
        //given
        String existingItemId = "010M";

        //when
        Optional<StockItem> existingItem = stockRepository.findById(existingItemId);

        //then
        Assertions.assertTrue(existingItem.isPresent());
        Assertions.assertEquals(existingItemId, existingItem.get().getProduct().getId());
    }

    @Test
    public void shouldFindByIdWithIncorrectId() {
        //given
        String nonExistingItemId = "99999";

        //when
        Optional<StockItem> nonExistingItem = stockRepository.findById(nonExistingItemId);

        //then
        Assertions.assertTrue(nonExistingItem.isEmpty());
    }


    @Test
    public void shouldSaveStockItemByIdWithDifferentQuantity() {
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

    @Test
    public void shouldFindCorrectStockItemFromSetOfId() {
        //given
        Set<String> ids = Set.of("001B", "004M", "015X");
        int expectedSize = 2;

        //when
        List<StockItem> stockItems = stockRepository.findByIdIn(ids);

        //then
        Assertions.assertEquals(expectedSize, stockItems.size());

    }


}