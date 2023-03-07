package com.shortlink.service.server;

import java.util.HashMap;
import java.util.Random;

public class RandomLtS {
    HashMap<String, String> codeMap = new HashMap<String, String>();
    int Length = 5;

    private char randomChar(){
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        return c;
    }

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        String shortUrl = "http://localhost:7000/stl/";
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
