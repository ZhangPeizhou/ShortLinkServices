package com.shortlink.service.server;

import com.shortlink.service.data.UrlMap;
import com.shortlink.service.data.UrlMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UrlConversionServer {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UrlMapRepository urlRepository;

    RandomLtS randomLtS = new RandomLtS();
    Base62LtS base62LtS = new Base62LtS();

    public void addToCache(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public Object findInCache(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Cacheable("urlMapping")
    public String randomLtS(String longUrl){
        Object value = findInCache(longUrl);
        if(value==null){
            System.out.println("Not in Cache");
            String shortUrl = "";
            UrlMap urlPair = urlRepository.findByLongUrl(longUrl);
            if(urlPair == null){
                System.out.println("Not in database");
                shortUrl = randomLtS.encode(longUrl);
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
    public String base62LtS(String longUrl){
        Object value = findInCache(longUrl);
        if(value==null){
            System.out.println("Not in Cache");
            String shortUrl = "";
            UrlMap urlPair = urlRepository.findByLongUrl(longUrl);
            if(urlPair == null){
                System.out.println("Not in database");
                shortUrl = base62LtS.encode(longUrl);
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
    public String shortToLong(String shortUrl){
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
