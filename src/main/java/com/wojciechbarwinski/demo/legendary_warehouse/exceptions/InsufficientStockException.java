package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class InsufficientStockException extends ApplicationException {
    private final List<InsufficientStockDTO> insufficientStockDTOs;


    public InsufficientStockException(List<InsufficientStockDTO> insufficientStockDTOs) {
        super("There is insufficient stock for some ordered products in our warehouse.");
        this.insufficientStockDTOs = insufficientStockDTOs;
    }

}
