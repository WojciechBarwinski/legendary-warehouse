package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record ShipmentInfo(
        Long orderId,
        ShipmentStatus status
) {}