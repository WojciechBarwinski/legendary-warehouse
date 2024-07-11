package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record InsufficientProductDTO(String productId, int expectedQuantity, int stockQuantity) {
}
