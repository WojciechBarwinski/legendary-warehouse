package com.wojciechbarwinski.demo.legendary_warehouse.controllers;


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
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {


    private final OrderService orderService;
    private final WebClient webClient;

    @PostMapping("/order")
    public ResponseEntity<Void> correctOrder(@RequestBody OrderDTO order) {

        orderService.proceedOrder(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/order/shipment")
    public ResponseEntity<String> correctOrder(@RequestBody ShipmentInfo shipmentInfo) {

        String url = "http://localhost:8080/shipment";

        String responseBody = webClient.post()
                .uri(url)
                .bodyValue(shipmentInfo)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok("Paczka została wysłana do aplikacji B: " + responseBody); //TODO what to return when something goes wrong?
    }

}
