package com.shortlink.service.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Random;

@Component
public class RandomLtS {
    HashMap<String, String> codeMap = new HashMap<String, String>();
    int Length = 5;

    @Value("${shortToLongUrl}")
    private String shortToLongUrlValue;

    private char randomChar(){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String shortUrl = shortToLongUrlValue;
        System.out.println("v: "+shortToLongUrlValue);
        System.out.println("shortUrl: "+shortUrl);
        for(int i=0; i<Length; i++){
            shortUrl += randomChar();
        }
        codeMap.put(shortUrl, longUrl);
        return shortUrl;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        String longUrl = codeMap.get(shortUrl);
        return longUrl;
    }
}
