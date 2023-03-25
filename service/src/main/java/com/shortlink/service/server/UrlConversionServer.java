package com.shortlink.service.server;

import com.shortlink.service.data.UrlMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class UrlConversionServer {

    @Autowired
    RandomLtS randomLtS;

    @Autowired
    Base62LtS base62LtS;

    public String randomLtS(String longUrl){
        return randomLtS.encode(longUrl);
    }

    public String base62LtS(long longId){
        return base62LtS.encode(longId);
    }
}
