package com.wojciechbarwinski.demo.legendary_warehouse.controllers;


import com.wojciechbarwinski.demo.legendary_warehouse.dtos.MissingProductDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderLineDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/lw")
public class OrderController {

    @PostMapping("/order")
    public ResponseEntity<?> correctOrder(@RequestBody OrderDTO order) {

        if (checkIfOrderContainProductWithIDToIncorrectOrder(order.getOrderLines())) {
            return new ResponseEntity<>(createMissingProducts(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkIfOrderContainProductWithIDToIncorrectOrder(List<OrderLineDTO> orderLines) {
        String incorrectProductID = "007";
        return orderLines.stream()
                .anyMatch(orderLineDTO -> orderLineDTO.productID().equals(incorrectProductID));
    }

    private List<MissingProductDTO> createMissingProducts() {
        List<MissingProductDTO> missingProductDTOList = new ArrayList<>();
        missingProductDTOList.add(new MissingProductDTO("007", 3, 1));
        return missingProductDTOList;
    }
}
