package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

public record InsufficientStockDTO(Long productId, int expectedQuantity, int stockQuantity) {
}
