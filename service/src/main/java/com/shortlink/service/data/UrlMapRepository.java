package com.shortlink.service.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlMapRepository  extends MongoRepository<UrlMap, String> {
    UrlMap findByLongUrl(String longUrl);
    UrlMap findByShortUrl(String shortUrl);
}
