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
public class OrderServiceImpl implements OrderService {

    private final StockRepository stockRepository;

    public OrderServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void proceedOrder(OrderDTO order) {

        checkIfProductsInStockAreAvailable(order);

        removeQuantityOfItemsFromStock(order);
    }

    private void checkIfProductsInStockAreAvailable(OrderDTO order) {
        List<String> missingProducts = new ArrayList<>();
        List<InsufficientStockDTO> insufficientStockProducts = new ArrayList<>();

        for (OrderLineDTO orderLine : order.getOrderLines()) {
            Optional<StockItem> itemFromStock = stockRepository.findById(orderLine.productID());
            if (itemFromStock.isEmpty()) {
                missingProducts.add(orderLine.productID());
            }
            else if (itemFromStock.get().getQuantity() < orderLine.quantity()) {
                insufficientStockProducts.add(new InsufficientStockDTO(orderLine.productID(), orderLine.quantity(), itemFromStock.get().getQuantity()));
            }
        }

        if (!missingProducts.isEmpty()) {
            throw new ProductNotFoundException("Some of the ordered products are not available in our database.", missingProducts);
        }
        if (!insufficientStockProducts.isEmpty()) {
            throw new InsufficientStockException("There is insufficient stock for some ordered products in our warehouse.", insufficientStockProducts);
        }
    }

    private void removeQuantityOfItemsFromStock(OrderDTO order) {
        for (OrderLineDTO orderLine : order.getOrderLines()) {
            StockItem stockItem = stockRepository.findById(orderLine.productID()).get();

            int newQuantityInStock = stockItem.getQuantity() - orderLine.quantity();
            stockItem.setQuantity(newQuantityInStock);

            stockRepository.save(stockItem);
        }
    }

}
