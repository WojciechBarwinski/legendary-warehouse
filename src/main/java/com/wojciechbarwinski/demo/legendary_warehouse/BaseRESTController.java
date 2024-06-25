package com.wojciechbarwinski.demo.legendary_warehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class BaseRESTController {


    @GetMapping()
    public String getHello(){
        return "Hello World";
    }
}
