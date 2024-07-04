package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.MissingProductDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class MissingProductException  extends ApplicationException{
    private final List<MissingProductDTO> MISSING_PRODUCTS;
    private final String MESSAGE;

    public MissingProductException(String message, List<MissingProductDTO> missingProductList) {
        this.MESSAGE = message;
        this.MISSING_PRODUCTS = missingProductList;
    }

}
