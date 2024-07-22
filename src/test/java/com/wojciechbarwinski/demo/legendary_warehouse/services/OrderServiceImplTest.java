package com.wojciechbarwinski.demo.legendary_warehouse.services;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ProductNotFoundException;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.Product;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockItem;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderServiceImpl(stockRepository);
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductsDoNotExist() {
        //given
        String wrongId1 = "111";
        String wrongId2 = "222";
        int unimportantQuantity = 5;
        List<OrderLineDTO> orderLineDTOS = List.of(
                new OrderLineDTO(wrongId1, unimportantQuantity),
                new OrderLineDTO(wrongId2, unimportantQuantity)
        );
        OrderDTO order = new OrderDTO(null, orderLineDTOS);

        when(stockRepository.findByIdIn(Set.of(wrongId1, wrongId2))).thenReturn(List.of());

        //when
        ProductNotFoundException exception1 = assertThrows(ProductNotFoundException.class, () -> orderService.proceedOrder(order));

        //then
        assertEquals("Some of the ordered products are not available in our database.", exception1.getMessage());
        assertTrue(exception1.getMissingProducts().contains(wrongId1));
        assertTrue(exception1.getMissingProducts().contains(wrongId2));
    }

    @Test
    void shouldThrowInsufficientStockExceptionWhenOrderQuantityExceedsStock() {
        //given
        int expectedErrorListSize = 2;
        int orderQuantity1 = 15;
        int orderQuantity2 = 20;
        int orderQuantity3 = 5;
        String productId1 = "001B";
        String productId2 = "002B";
        String productId3 = "003B";
        List<OrderLineDTO> orderLineDTOS = List.of(
                new OrderLineDTO(productId1, orderQuantity1),
                new OrderLineDTO(productId2, orderQuantity2),
                new OrderLineDTO(productId3, orderQuantity3)
        );

        OrderDTO order = new OrderDTO(null, orderLineDTOS);
        StockItem stockItem1 = new StockItem(new Product(productId1, "name1"), 10);
        StockItem stockItem2 = new StockItem(new Product(productId2, "name2"), 10);
        StockItem stockItem3 = new StockItem(new Product(productId3, "name3"), 10);

        when(stockRepository.findByIdIn(Set.of(productId1, productId2, productId3)))
                .thenReturn(List.of(stockItem1, stockItem2, stockItem3));

        //when
        InsufficientStockException exception = assertThrows(InsufficientStockException.class, () -> orderService.proceedOrder(order));

        //then
        assertEquals("There is insufficient stock for some ordered products in our warehouse.", exception.getMessage());
        assertEquals(exception.getInsufficientStockDTOs().size(), expectedErrorListSize);
        assertEquals(exception.getInsufficientStockDTOs().get(0).productId(), productId1);
    }

    @Test
    void shouldProceedOrderAndReduceStockQuantity() {
        //given
        String productID = "001B";
        int quantityFromOrder = 6;
        int expectedQuantityAfterProceedOrder = 4;
        OrderLineDTO orderLine = new OrderLineDTO(productID, quantityFromOrder);
        OrderDTO order = new OrderDTO(null, List.of(orderLine));
        StockItem stockItem = new StockItem(new Product(productID, "Gloomheaven"), 10);

        when(stockRepository.findByIdIn(Set.of(productID))).thenReturn(List.of(stockItem));

        //when
        orderService.proceedOrder(order);

        //then
        assertEquals(expectedQuantityAfterProceedOrder, stockItem.getQuantity());
        verify(stockRepository).save(stockItem);
    }

}