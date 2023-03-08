package com.shortlink.service.server;

import com.shortlink.service.data.UrlMap;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UrlConversionServer {

    RandomLtS randomLtS = new RandomLtS();
    Base62LtS base62LtS = new Base62LtS();

    public String randomLtS(String longUrl){
        return randomLtS.encode(longUrl);
    }

    public String base62LtS(String longUrl){
        return base62LtS.encode(longUrl);
    }
}
