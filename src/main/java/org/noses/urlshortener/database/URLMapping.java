package org.noses.urlshortener.database;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ToString
@Data
@Entity
public class URLMapping {

    @Id
    @NotNull
    @Column
    private String slug;

    private String fullSourceURL;

    private String destinationURL;

    private Date created;

    private Date lastModified;

    private Integer numTimesAccessed;

    private String interpretedDestinationURL;
}
