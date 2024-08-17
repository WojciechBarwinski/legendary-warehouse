package com.wojciechbarwinski.demo.legendary_warehouse.controllers;


import com.wojciechbarwinski.demo.legendary_warehouse.EpicBoarGameShopClient;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.OrderDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.dtos.ShipmentInfo;
import com.wojciechbarwinski.demo.legendary_warehouse.services.OrderService;
import feign.FeignException;
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
    String shipmentStatusUpdate(@RequestBody ShipmentInfo shipmentInfo) {

        //TODO to musisz mi chyba bardziej wyjasnić jak to działa i co i jak powinno byc zwracane.
        // Tutaj wystarczyła by 200 ale czy to ma być string? Jak nie działa to wiem ze leci wyjątek (patrz niżej) ale też widziałem jak jakiś gość wszedzie wrzuca RespondeEntity??
        // Ale czy wtedy w EBGS musiałbym metodę też ustawiać na ResponseEntity? Czy czasem spring sam tego nie konwertuje?
        try {
            return client.updateShipment(shipmentInfo);
        } catch (FeignException e) {
            return "Error occurred while updating shipment";
        }
    }
}
