package com.wojciechbarwinski.demo.legendary_warehouse.stock;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
class Product {

    private String id;
    private String name;
}
