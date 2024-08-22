package com.wojciechbarwinski.demo.legendary_warehouse.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private AddressDTO addressDTO;
    private List<OrderLineDTO> orderLineDTOS;
}
