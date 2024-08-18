package com.wojciechbarwinski.demo.legendary_warehouse.controllers;


import com.wojciechbarwinski.demo.legendary_warehouse.EpicBoarGameShopClient;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.ShipmentInfo;
import com.wojciechbarwinski.demo.legendary_warehouse.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final EpicBoarGameShopClient client;


    @PostMapping("")
    public ResponseEntity<Void> proceedOrder(@RequestBody OrderDTO order) {

        orderService.proceedOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/shipment")
    ResponseEntity<Void> shipmentStatusUpdate(@RequestBody ShipmentInfo shipmentInfo) {

        return client.updateShipment(shipmentInfo);
    }
}
