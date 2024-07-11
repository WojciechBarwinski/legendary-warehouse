package com.wojciechbarwinski.demo.legendary_warehouse.services;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ProductNotFoundException;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockItem;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void stockCheck(OrderDTO order) {
        checkProductAvailability(order);
        checkSufficientStock(order);

    }

    private void checkProductAvailability(OrderDTO order) {
        List<String> productNotFoundList = new ArrayList<>();

        for (OrderLineDTO orderLine : order.getOrderLines()) {
            Optional<StockItem> itemFromStock = stockRepository.findById(orderLine.productID());
            if (itemFromStock.isEmpty()) {
                productNotFoundList.add(orderLine.productID());
            }
        }

        if (!productNotFoundList.isEmpty()) {
            throw new ProductNotFoundException("Some of the ordered products are not available in our database.", productNotFoundList);
        }
    }

    private void checkSufficientStock(OrderDTO order) {
        List<InsufficientStockDTO> insufficientStockList = new ArrayList<>();

        for (OrderLineDTO orderLine : order.getOrderLines()) {
            Optional<StockItem> itemFromStock = stockRepository.findById(orderLine.productID());
            if (itemFromStock.isPresent() && itemFromStock.get().getQuantity() < orderLine.quantity()) {
                insufficientStockList.add(new InsufficientStockDTO(orderLine.productID(), orderLine.quantity(), itemFromStock.get().getQuantity()));
            }
        }

        if (!insufficientStockList.isEmpty()) {
            throw new InsufficientStockException("There is insufficient stock for some ordered products in our warehouse.", insufficientStockList);
        }
    }
}
