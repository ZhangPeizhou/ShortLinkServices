package com.shortlink.service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UrlConversionController {

    @GetMapping("/shortToLong")
    public String shortToLong(@RequestBody String shortUrl){
        System.out.println(shortUrl);
        return shortUrl;
    }

    @GetMapping("/longToShort")
    public String LongToShort(@RequestBody String longUrl){
        System.out.println(longUrl);
        return longUrl;
    }
}
