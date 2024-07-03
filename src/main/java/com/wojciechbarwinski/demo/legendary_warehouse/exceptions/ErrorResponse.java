package com.wojciechbarwinski.demo.legendary_warehouse.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse<T>{
    private int errorCode;
    private String errorMessage;
    private T details;
}
