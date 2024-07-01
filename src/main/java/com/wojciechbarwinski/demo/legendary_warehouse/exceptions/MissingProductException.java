package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.MissingProductDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class MissingProductException  extends ApplicationException{
    private final List<MissingProductDTO> missingProductList;

    public MissingProductException(List<MissingProductDTO> missingProductList) {
        this.missingProductList = missingProductList;
    }

}
