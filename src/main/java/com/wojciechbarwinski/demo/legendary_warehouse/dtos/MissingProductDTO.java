package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record MissingProductDTO(String productId, int expectedQuantity, int stockQuantity) {
}
