package com.shortlink.service.services;

import com.shortlink.service.data.UrlMap;
import com.shortlink.service.data.UrlMapRepository;
import com.shortlink.service.exception.URLInvalidException;
import com.shortlink.service.server.UrlConversionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

@Component
public class Services {
    @Autowired
    @Qualifier("script")
    private RedisScript<Long> script;

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

    //increase SequenceId by using lua script
    public long getSequenceIdByScript() {
        long sequenceId = redisTemplate.execute(script, List.of("counter"));
        redisTemplate.getConnectionFactory().getConnection().bgSave();
        System.out.println(sequenceId);
        return  sequenceId;
    }

    public boolean isValidURL(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    @Cacheable("urlMapping")
    public String LongToShort(String longUrl, String method) throws Exception {
        System.out.println(longUrl);
        boolean urlIsValid = isValidURL(longUrl);
        if(urlIsValid){
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
                        //shortUrl = urlSConvertServer.base62LtS(longUrl);
                        shortUrl = urlSConvertServer.base62LtS(getSequenceIdByScript());
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
        }else{
            throw new URLInvalidException("ERROR MESSAGE: URL Invalid");
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
