package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record ShipmentInfo(

        String shipmentId, //for this moment it is the same as OrderId.
        ShipmentStatus status
) {}
