package com.oscar.patito.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/patito")
public class PatitoRestApi {

    @GetMapping("printTest")
    public String printTest(){
        return "Hello!";
    }
}
