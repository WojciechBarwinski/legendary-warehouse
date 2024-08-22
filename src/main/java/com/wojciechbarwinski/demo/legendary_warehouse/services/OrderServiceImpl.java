package com.wojciechbarwinski.demo.legendary_warehouse.services;


import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ProductNotFoundException;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockItem;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final StockRepository stockRepository;

    public void proceedOrder(OrderDTO order) {

        Set<Long> collect = order.getOrderLineDTOS().stream()
                .map(OrderLineDTO::productId)
                .collect(Collectors.toSet());

        List<StockItem> itemsFromDB = stockRepository.findByIdIn(collect);

        checkIfProductsInStockAreAvailable(order, itemsFromDB);
        removeQuantityOfItemsFromStock(order, itemsFromDB);
    }

    private void checkIfProductsInStockAreAvailable(OrderDTO order, List<StockItem> itemsFromDB) {
        List<Long> missingProducts = new ArrayList<>();
        List<InsufficientStockDTO> insufficientStockProducts = new ArrayList<>();

        for (OrderLineDTO orderLine : order.getOrderLineDTOS()) {

            Optional<StockItem> itemFromStock = itemsFromDB.stream()
                    .filter(x -> orderLine.productId().equals(x.getProduct().getId()))
                    .findFirst();

            if (itemFromStock.isEmpty()) {
                missingProducts.add(orderLine.productId());
            } else if (itemFromStock.get().getQuantity() < orderLine.quantity()) {
                insufficientStockProducts.add(new InsufficientStockDTO(orderLine.productId(), orderLine.quantity(), itemFromStock.get().getQuantity()));
            }
        }

        if (!missingProducts.isEmpty()) {
            throw new ProductNotFoundException(missingProducts);
        }
        if (!insufficientStockProducts.isEmpty()) {
            throw new InsufficientStockException(insufficientStockProducts);
        }
    }

    private void removeQuantityOfItemsFromStock(OrderDTO order, List<StockItem> itemsFromDB) {
        for (OrderLineDTO orderLine : order.getOrderLineDTOS()) {

            StockItem stockItem = itemsFromDB.stream()
                    .filter(x-> orderLine.productId().equals(x.getProduct().getId()))
                    .findFirst().get();

            int newQuantityInStock = stockItem.getQuantity() - orderLine.quantity();
            stockItem.setQuantity(newQuantityInStock);

            stockRepository.save(stockItem);
        }
    }

}
