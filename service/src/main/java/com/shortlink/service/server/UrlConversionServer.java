package com.shortlink.service.server;

import org.springframework.stereotype.Component;

@Component
public class UrlConversionServer {
    RandomLtS randomLtS = new RandomLtS();
    Base62LtS base62LtS = new Base62LtS();

    public String randomLtS(String longUrl){
        String shortUrl = randomLtS.encode(longUrl);
        return shortUrl;
    }

    public String base62LtS(String longUrl){
        String shortUrl = base62LtS.encode(longUrl);
        return shortUrl;
    }
}
