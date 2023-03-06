package com.shortlink.service.controller;

import com.shortlink.service.server.UrlConversionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UrlConversionController {

    @Autowired
    UrlConversionServer urlSConvertServer;

    @GetMapping("/shortToLong")
    public void shortToLong(@RequestParam String url, HttpServletResponse response) throws IOException {
        System.out.println(url);
        response.sendRedirect(url);
    }

    @PostMapping("/longToShort")
    public String LongToShort(@RequestParam("url") String url, @RequestParam("method") String method){
        System.out.println(url);
        String shortUrl;
        if(method.equals("random")){
            shortUrl = urlSConvertServer.randomLtS(url);
        }else if(method.equals("base62")){
            shortUrl = urlSConvertServer.base62LtS(url);
        }else{
            shortUrl = method;
        }
        return shortUrl;
    }
}
