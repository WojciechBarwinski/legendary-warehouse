package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import lombok.Getter;

import java.util.List;


@Getter
public class ProductNotFoundException extends ApplicationException {

    private final List<String> missingProducts;

    public ProductNotFoundException(List<String> missingProductList) {
        super("Some of the ordered products are not available in our database.");
        this.missingProducts = missingProductList;
    }
}
