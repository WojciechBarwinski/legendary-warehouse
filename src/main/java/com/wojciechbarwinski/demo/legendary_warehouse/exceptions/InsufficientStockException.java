package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InsufficientStockException extends ApplicationException {
    private final List<InsufficientStockDTO> insufficientStockDTOs;
    private final String message;

    public InsufficientStockException(List<InsufficientStockDTO> insufficientStockDTOs) {
        this.message = "There is insufficient stock for some ordered products in our warehouse.";
        this.insufficientStockDTOs = insufficientStockDTOs;
    }

}
