package com.shortlink.service.services;

import com.shortlink.service.data.UrlMap;
import com.shortlink.service.data.UrlMapRepository;
import com.shortlink.service.server.UrlConversionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class Services {

    @Autowired
    UrlConversionServer urlSConvertServer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UrlMapRepository urlRepository;

    public Object findInCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public void addToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Cacheable("urlMapping")
    public String LongToShort(String longUrl, String method){
        System.out.println(longUrl);
        Object value = findInCache(longUrl);
        String shortUrl;
        if(value==null){
            System.out.println("Not in Cache");
            UrlMap urlPair = urlRepository.findByLongUrl(longUrl);
            if(urlPair == null) {
                System.out.println("Not in database");
                if(method.equals("random")){
                    shortUrl = urlSConvertServer.randomLtS(longUrl);
                }else if(method.equals("base62")){
                    shortUrl = urlSConvertServer.base62LtS(longUrl);
                }else{
                    shortUrl = method;
                }
                UrlMap newPair = new UrlMap(longUrl, shortUrl);
                urlRepository.save(newPair);
            }else{
                System.out.println("In the database");
                shortUrl = urlPair.getShortUrl();
            }
            addToCache(longUrl, shortUrl);
            addToCache(shortUrl, longUrl);
            return shortUrl;
        }else{
            System.out.println("Already in Cache");
            return value.toString();
        }
    }

    @Cacheable("urlMapping")
    public String ShortToLong(String shortUrl){
        System.out.println(shortUrl);
        Object value = findInCache(shortUrl);
        if(value == null){
            System.out.println("Not in Cache");
            String longUrl = "";
            UrlMap urlPair = urlRepository.findByShortUrl(shortUrl);
            if(urlPair == null){
                System.out.println("Not in database; ERROR");
                longUrl = "ERROR: url not found";
            }else{
                System.out.println("In the database");
                longUrl = urlPair.getLongUrl();
            }
            addToCache(longUrl, shortUrl);
            addToCache(shortUrl, longUrl);
            return longUrl;
        }else{
            System.out.println("Already in Cache");
            return value.toString();
        }
    }
}
