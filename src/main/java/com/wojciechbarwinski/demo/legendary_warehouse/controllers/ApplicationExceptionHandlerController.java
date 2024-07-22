package com.wojciechbarwinski.demo.legendary_warehouse.controllers;

import com.wojciechbarwinski.demo.legendary_warehouse.dtos.InsufficientStockDTO;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ErrorResponse;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.InsufficientStockException;
import com.wojciechbarwinski.demo.legendary_warehouse.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ControllerAdvice
public class ApplicationExceptionHandlerController {


    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(InsufficientStockException.class)
    public ErrorResponse<List<InsufficientStockDTO>> insufficientStockException(InsufficientStockException exception) {

        return new ErrorResponse<>(exception.getMessage(), exception.getInsufficientStockDTOs());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // 422
    @ExceptionHandler(ProductNotFoundException.class)
    public ErrorResponse<List<String>> productNotFoundException(ProductNotFoundException exception) {

        return new ErrorResponse<>(exception.getMessage(), exception.getMissingProducts());
    }
}
