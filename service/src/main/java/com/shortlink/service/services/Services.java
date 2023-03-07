package com.shortlink.service.services;

import com.shortlink.service.server.UrlConversionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services {

    @Autowired
    UrlConversionServer urlSConvertServer;

    public String LongToShort(String url, String method){
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

    public String ShortToLong(String shortUrl){
        return urlSConvertServer.shortToLong(shortUrl);
    }
}
