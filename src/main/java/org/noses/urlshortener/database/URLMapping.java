package org.noses.urlshortener.database;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@RedisHash("URLMapping")
@ToString
@Data
public class URLMapping {

    @Id
    private String slug;
    private String fullSourceURL;

    private String destinationURL;

    private Date created;

    private Date lastModified;

    private Integer numTimesAccessed;

    private String interpretedDestinationURL;
}
