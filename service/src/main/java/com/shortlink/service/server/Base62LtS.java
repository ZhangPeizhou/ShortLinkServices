package com.shortlink.service.server;

import org.springframework.beans.factory.annotation.Value;

public class Base62LtS {
    @Value("${shortToLongUrl}")
    private String shortToLongUrlValue;

    private final String BASE62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String base62(long num) {
        String result = "";
        long temp = num;
        while( temp/62 != 0){
            result = BASE62_CHARS.charAt((int) (temp %62)) + result;
            temp = temp/62;
        }
        result = BASE62_CHARS.charAt((int) temp) + result;
        return result;
    }

    public String encode(String url){
        long numUrl = url.hashCode();
        String result = base62(numUrl);
        return  shortToLongUrlValue+result;
    }

    /*
    * Test: (https://microsoft.github.io/makecode-csp/unit-6/day-14/base63-url-shorteners)
    * 100 -> 1C
    * 1000 -> g8
    * 93000 -> oc0
    * 123456789089898 -> z3wBXxG2
    * */
}
