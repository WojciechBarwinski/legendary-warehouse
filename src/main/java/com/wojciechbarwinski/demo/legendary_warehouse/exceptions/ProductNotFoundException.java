package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import lombok.Getter;

import java.util.List;


@Getter
public class ProductNotFoundException extends ApplicationException {

    private final List<Long> missingProducts;

    public ProductNotFoundException(List<Long> missingProductList) {
        super("Some of the ordered products are not available in our database.");
        this.missingProducts = missingProductList;
    }
}
