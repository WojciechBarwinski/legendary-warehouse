package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;


class InMemoryStockRepositoryTest {

    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        stockRepository = new InMemoryStockRepository();
    }

    @Test
    public void shouldContainInitialData() {
        //given
        Long existingItemId = 1L;

        //when
        Optional<StockItem> itemFromDB = stockRepository.findById(existingItemId);

        //then
        Assertions.assertTrue(itemFromDB.isPresent());
        Assertions.assertEquals(existingItemId, itemFromDB.get().getProduct().getId());
    }

    @Test
    public void shouldFindByIdWithCorrectId() {
        //given
        Long existingItemId = 1L;

        //when
        Optional<StockItem> existingItem = stockRepository.findById(existingItemId);

        //then
        Assertions.assertTrue(existingItem.isPresent());
        Assertions.assertEquals(existingItemId, existingItem.get().getProduct().getId());
    }

    @Test
    public void shouldFindByIdWithIncorrectId() {
        //given
        Long nonExistingItemId = 999L;

        //when
        Optional<StockItem> nonExistingItem = stockRepository.findById(nonExistingItemId);

        //then
        Assertions.assertTrue(nonExistingItem.isEmpty());
    }


    @Test
    public void shouldSaveStockItemByIdWithDifferentQuantity() {
        //given
        int newQuantity = 3;
        Long orderedProductId = 1L;
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
        Set<Long> ids = Set.of(1L, 2L, 999L);
        int expectedSize = 2;

        //when
        List<StockItem> stockItems = stockRepository.findByIdIn(ids);

        //then
        Assertions.assertEquals(expectedSize, stockItems.size());

    }


}