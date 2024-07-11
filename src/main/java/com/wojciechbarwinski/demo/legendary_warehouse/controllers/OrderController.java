package com.wojciechbarwinski.demo.legendary_warehouse.controllers;


import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientProductDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final String INCORRECT_PRODUCT_ID = "007";

    @PostMapping("/order")
    public ResponseEntity<Void> correctOrder(@RequestBody OrderDTO order) {

        if (checkIfOrderContainProductWithIDToIncorrectOrder(order.getOrderLines())) {
            throw new InsufficientStockException("Missing some of ordered products", createMissingProducts());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkIfOrderContainProductWithIDToIncorrectOrder(List<OrderLineDTO> orderLines) {

        return orderLines.stream()
                .anyMatch(orderLineDTO -> orderLineDTO.productID().equals(INCORRECT_PRODUCT_ID));
    }

    private List<InsufficientProductDTO> createMissingProducts() {
        List<InsufficientProductDTO> insufficientProductDTOList = new ArrayList<>();
        insufficientProductDTOList.add(new InsufficientProductDTO(INCORRECT_PRODUCT_ID, 3, 1));
        return insufficientProductDTOList;
    }
}
