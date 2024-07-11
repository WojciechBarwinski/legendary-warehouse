package com.wojciechbarwinski.demo.legendary_warehouse.services;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ProductNotFoundException;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.Product;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockItem;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    @Test
    void checkProductAvailabilityWhenProductIdIsIncorrect() {
        String wrongId1 = "111";
        String wrongId2 = "222";
        int unimportantQuantity = 5;
        List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
        orderLineDTOS.add(new OrderLineDTO(wrongId1, unimportantQuantity));
        orderLineDTOS.add(new OrderLineDTO(wrongId2, unimportantQuantity));
        OrderDTO order = new OrderDTO();
        order.setOrderLines(orderLineDTOS);

        when(stockRepository.findById(wrongId1)).thenReturn(Optional.empty());
        when(stockRepository.findById(wrongId2)).thenReturn(Optional.empty());
        ProductNotFoundException exception1 = assertThrows(ProductNotFoundException.class, () -> stockService.stockCheck(order));
        ProductNotFoundException exception2 = assertThrows(ProductNotFoundException.class, () -> stockService.stockCheck(order));

        assertEquals("Some of the ordered products are not available in our database.", exception1.getMessage());
        assertTrue(exception1.getMissingProducts().contains(wrongId1));
        assertTrue(exception2.getMissingProducts().contains(wrongId2));
    }

    @Test
    void checkInsufficientStock() {
        int expectedErrorListSize = 2;
        int wrongQuantity1 = 15;
        int wrongQuantity2 = 20;
        String correctId1 = "001B";
        String correctId2 = "002B";
        List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
        orderLineDTOS.add(new OrderLineDTO(correctId1, wrongQuantity1));
        orderLineDTOS.add(new OrderLineDTO(correctId2, wrongQuantity2));
        OrderDTO order = new OrderDTO();
        order.setOrderLines(orderLineDTOS);

        StockItem stockItem1 = new StockItem(new Product(correctId1, "name1"), 10);
        StockItem stockItem2 = new StockItem(new Product(correctId1, "name2"), 10);
        when(stockRepository.findById(correctId1)).thenReturn(Optional.of(stockItem1));
        when(stockRepository.findById(correctId2)).thenReturn(Optional.of(stockItem2));

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> stockService.stockCheck(order));

        assertEquals("There is insufficient stock for some ordered products in our warehouse.", exception.getMessage());
        assertEquals(exception.getInsufficientStock().size(), expectedErrorListSize);
        assertEquals(exception.getInsufficientStock().get(0).productId(), correctId1);
    }
}