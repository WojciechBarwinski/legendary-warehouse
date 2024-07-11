package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientProductDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InsufficientStockException extends ApplicationException {
    private final List<InsufficientProductDTO> insufficientStock;
    private final String message;

    public InsufficientStockException(String message, List<InsufficientProductDTO> insufficientStock) {
        this.message = message;
        this.insufficientStock = insufficientStock;
    }

}
