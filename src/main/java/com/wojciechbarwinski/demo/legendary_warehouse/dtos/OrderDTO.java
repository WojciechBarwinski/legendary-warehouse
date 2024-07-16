package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    private String address;
    private List<OrderLineDTO> orderLines;
}
