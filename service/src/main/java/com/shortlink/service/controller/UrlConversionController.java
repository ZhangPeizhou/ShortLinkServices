package com.shortlink.service.controller;

import com.shortlink.service.services.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UrlConversionController {

    @Autowired
    Services services;

    @GetMapping("/stl/*")
    public void shortToLong(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String longUrl = services.ShortToLong(request.getRequestURL().toString());
        response.sendRedirect(longUrl);
    }

    @PostMapping("/longToShort")
    public String LongToShort(@RequestParam("url") String url, @RequestParam("method") String method){
        return services.LongToShort(url, method);
    }
}
