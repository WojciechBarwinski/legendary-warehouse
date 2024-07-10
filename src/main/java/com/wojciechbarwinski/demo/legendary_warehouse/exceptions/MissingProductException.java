package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.MissingProductDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class MissingProductException  extends ApplicationException{
    private final List<MissingProductDTO> missingProducts;
    private final String message;

    public MissingProductException(String message, List<MissingProductDTO> missingProductList) {
        this.message = message;
        this.missingProducts = missingProductList;
    }

}
