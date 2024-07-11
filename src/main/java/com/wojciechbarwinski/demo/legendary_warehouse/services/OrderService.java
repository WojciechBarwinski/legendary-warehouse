package com.wojciechbarwinski.demo.legendary_warehouse.services;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;


public interface OrderService {

    void proceedOrder(OrderDTO order);
}
