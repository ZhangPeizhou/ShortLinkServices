package com.shortlink.service.controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UrlConversionController {

    @GetMapping("/shortToLong")
    public String shortToLong(@RequestParam String url){
        System.out.println(url);
        return url;
    }

    @GetMapping("/longToShort")
    public String LongToShort(@RequestParam String url){
        System.out.println(url);
        return url;
    }
}
