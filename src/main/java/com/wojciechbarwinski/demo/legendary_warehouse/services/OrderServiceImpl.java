package com.wojciechbarwinski.demo.legendary_warehouse.services;


import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockItem;
import com.wojciechbarwinski.demo.legendary_warehouse.stock.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final StockRepository stockRepository;
    private final StockService stockService;

    public OrderServiceImpl(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    public void proceedOrder(OrderDTO order) {

        stockService.stockCheck(order);

        for (OrderLineDTO orderLine : order.getOrderLines()) {
            StockItem stockItem = stockRepository.findById(orderLine.productID()).get();

            int newQuantityInStock = stockItem.getQuantity() - orderLine.quantity();
            stockItem.setQuantity(newQuantityInStock);

            stockRepository.save(stockItem);
        }
    }

}
