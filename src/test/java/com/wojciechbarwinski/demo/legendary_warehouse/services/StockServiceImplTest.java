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
    void shouldThrowExceptionWhenProductDoesNotExist() {
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
    void shouldThrowExceptionWhenQuantityInOrderIsBiggerThenQuantityInStock() {
        int expectedErrorListSize = 2;
        int orderQuantity1  = 15;
        int orderQuantity2 = 20;
        String productId1 = "001B";
        String productId2 = "002B";
        List<OrderLineDTO> orderLineDTOS = new ArrayList<>();
        orderLineDTOS.add(new OrderLineDTO(productId1, orderQuantity1 ));
        orderLineDTOS.add(new OrderLineDTO(productId2, orderQuantity2));
        OrderDTO order = new OrderDTO();
        order.setOrderLines(orderLineDTOS);

        StockItem stockItem1 = new StockItem(new Product(productId1, "name1"), 10);
        StockItem stockItem2 = new StockItem(new Product(productId2, "name2"), 10);
        when(stockRepository.findById(productId1)).thenReturn(Optional.of(stockItem1));
        when(stockRepository.findById(productId2)).thenReturn(Optional.of(stockItem2));

        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> stockService.stockCheck(order));

        assertEquals("There is insufficient stock for some ordered products in our warehouse.", exception.getMessage());
        assertEquals(exception.getInsufficientStock().size(), expectedErrorListSize);
        assertEquals(exception.getInsufficientStock().get(0).productId(), productId1);
    }
}