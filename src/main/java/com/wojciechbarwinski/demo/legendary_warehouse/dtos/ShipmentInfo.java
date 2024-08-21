package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record ShipmentInfo(
        String shipmentId,
        ShipmentStatus status
) {}