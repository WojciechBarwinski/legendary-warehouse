package com.wojciechbarwinski.demo.legendary_warehouse;


import com.wojciechbarwinski.demo.legendary_warehouse.dtos.ShipmentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shipmentClient", url = "${ebgs.shipment.url")
public interface EpicBoarGameShopClient {

    @PostMapping("/shipment")
    String updateShipment(@RequestBody ShipmentInfo shipmentInfo);

}
