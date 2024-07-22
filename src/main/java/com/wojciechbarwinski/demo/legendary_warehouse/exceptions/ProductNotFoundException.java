package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import lombok.Getter;

import java.util.List;


@Getter
public class ProductNotFoundException extends ApplicationException {

    private final List<String> missingProducts;
    private final String message;

    public ProductNotFoundException(List<String> missingProductList) {
        this.message = "Some of the ordered products are not available in our database.";
        this.missingProducts = missingProductList;
    }
}
