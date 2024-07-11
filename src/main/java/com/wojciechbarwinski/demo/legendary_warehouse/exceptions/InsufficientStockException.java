package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InsufficientStockException extends ApplicationException {
    private final List<InsufficientStockDTO> insufficientStock;
    private final String message;

    public InsufficientStockException(String message, List<InsufficientStockDTO> insufficientStock) {
        this.message = message;
        this.insufficientStock = insufficientStock;
    }

}
