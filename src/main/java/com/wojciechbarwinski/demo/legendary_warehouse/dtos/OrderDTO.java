package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {
    private String address;
    private List<OrderLineDTO> orderLines;
}
